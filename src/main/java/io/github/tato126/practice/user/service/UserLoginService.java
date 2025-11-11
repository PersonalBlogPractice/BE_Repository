package io.github.tato126.practice.user.service;

import io.github.tato126.practice.common.excetion.login.DuplicateEmailException;
import io.github.tato126.practice.common.excetion.login.InvalidCredentialsException;
import io.github.tato126.practice.config.jwt.JwtUtil;
import io.github.tato126.practice.user.dto.request.UserRequest;
import io.github.tato126.practice.user.dto.response.LoginResponse;
import io.github.tato126.practice.user.dto.response.UserResponse;
import io.github.tato126.practice.user.entity.User;
import io.github.tato126.practice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 사용자 인증 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * <p>
 * 회원가입, 로그인 등의 기능을 제공하며, 비밀번호 암호화 및 JWT 토큰 생성을 처리합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 새로운 사용자를 등록합니다.
     * <p>
     * 이메일 중복 검사를 수행하고, 비밀번호를 암호화하여 저장합니다.
     * </p>
     *
     * @param request 회원가입 요청 정보 (이메일, 비밀번호, 닉네임, 자기소개)
     * @return 생성된 사용자 정보 (비밀번호 제외)
     * @throws DuplicateEmailException 이메일이 이미 존재하는 경우
     */
    public UserResponse signup(UserRequest request) {

        // 이미 동일한 이메일이 존재하나요?
        // 존재하면 예외 발생
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateEmailException(request.email());
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(request.password());

        // 인코딩한 데이터
        User newUser = User.form(request, encodedPassword);
        log.debug("signup newUser: {}", newUser);

        // 저장
        userRepository.save(newUser);

        // entity -> response
        return UserResponse.form(newUser);
    }

    /**
     * 사용자 로그인을 처리하고 JWT 액세스 토큰을 발급합니다.
     * <p>
     * 이메일과 비밀번호를 검증한 후, 유효한 경우 JWT 토큰을 생성하여 반환합니다.
     * 보안을 위해 이메일/비밀번호 오류를 구분하지 않고 동일한 예외를 발생시킵니다.
     * </p>
     *
     * @param request 로그인 요청 정보 (이메일, 비밀번호)
     * @return 사용자 정보와 JWT 액세스 토큰
     * @throws InvalidCredentialsException 이메일이 존재하지 않거나 비밀번호가 일치하지 않는 경우
     */
    public LoginResponse login(UserRequest request) {

        // 저장소에 등록 되어 있나요?
        // 보안을 위해 이메일/비밀번호 구분 없이 동일한 예외 발생
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException());

        // 패스워드가 일치하나요?
        // 보안을 위해 이메일/비밀번호 구분 없이 동일한 예외 발생
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // jwt 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());

        // LoginResponse 반환
        return LoginResponse.form(user, accessToken);
    }

    // 로그아웃
}
