package io.github.tato126.practice.user.controller;

import io.github.tato126.practice.user.dto.response.UserResponse;
import io.github.tato126.practice.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 정보 컨트롤러
 *
 * @author chan
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserProfileController {

    private final UserProfileService profileService;

    // 유저 프로필 조회
    @GetMapping("/me")
    public UserResponse getMyProfile(Authentication authentication) {

        // jwt 필터가 인증 완료한 사용자 아이디 추출
        String email = authentication.getName();

        log.debug("getMyProfile email: {}", email);

        return profileService.getUserProfileByEmail(email);
    }

    // 공개 프로필 조회 (공개 정보만)
    @GetMapping("/users/{userId}")
    public UserResponse getUserProfile(@PathVariable Long userId) {
        return profileService.getUserProfileById(userId);
    }
}
