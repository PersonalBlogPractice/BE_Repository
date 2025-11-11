package io.github.tato126.practice.user.service;

import io.github.tato126.practice.common.excetion.login.UserNotFoundException;
import io.github.tato126.practice.user.dto.response.UserResponse;
import io.github.tato126.practice.user.entity.User;
import io.github.tato126.practice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserRepository userRepository;

    // 유저 개인 조회
    public UserResponse getUserProfileByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        log.debug("[Service] findByUserEmail: {}", user);

        // user -> entity
        return UserResponse.form(user);
    }

    // 공개된 유저 조회
    public UserResponse getUserProfileById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return UserResponse.form(user);
    }
}
