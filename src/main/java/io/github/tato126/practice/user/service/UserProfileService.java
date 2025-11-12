package io.github.tato126.practice.user.service;

import io.github.tato126.practice.common.excetion.login.UserNotFoundException;
import io.github.tato126.practice.user.dto.response.UserResponse;
import io.github.tato126.practice.user.entity.User;
import io.github.tato126.practice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 사용자 프로필 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * <p>
 * 사용자 정보 조회(개인/공개), 프로필 수정 등의 기능을 제공합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserRepository userRepository;

    /**
     * 이메일로 사용자 프로필을 조회합니다.
     * <p>
     * 본인 인증 후 개인 정보를 조회할 때 사용합니다.
     * </p>
     *
     * @param email 조회할 사용자의 이메일
     * @return 사용자 프로필 정보
     * @throws UserNotFoundException 이메일에 해당하는 사용자가 없는 경우
     */
    public UserResponse getUserProfileByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        log.debug("[Service] findByUserEmail: {}", user);

        // user -> entity
        return UserResponse.form(user);
    }

    /**
     * 사용자 ID로 공개 프로필을 조회합니다.
     * <p>
     * 다른 사용자의 프로필을 조회할 때 사용합니다.
     * </p>
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자 프로필 정보
     * @throws UserNotFoundException 사용자가 존재하지 않는 경우
     */
    public UserResponse getUserProfileById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return UserResponse.form(user);
    }
}
