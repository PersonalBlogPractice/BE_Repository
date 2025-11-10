package io.github.tato126.practice.user.service;

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

@Slf4j
@RequiredArgsConstructor
@Service
public class UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // signup
    public UserResponse signup(UserRequest request) {

        // 이미 동일한 이메일이 존재하나요?
        // 존재하면 예외 발생
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("유효한 이메일이 아닙니다."); // TODO: 임시 에러 -> 글로벌 익셉션 핸들러
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

    // login
    public LoginResponse login(UserRequest request) {

        // 저장소에 등록 되어 있나요?
        // 등록되어 있지 않다면 예외 발생
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));

        // 패스워드가 일치하나요?
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // jwt 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());

        // LoginResponse 반환
        return LoginResponse.form(user, accessToken);
    }

    // 로그아웃
}
