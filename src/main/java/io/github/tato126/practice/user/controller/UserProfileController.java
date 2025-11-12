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
 * 사용자 프로필 관련 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * <p>
 * 본인 프로필 조회, 다른 사용자 공개 프로필 조회 등의 REST API 엔드포인트를 제공합니다.
 * 인증이 필요한 엔드포인트는 JWT 토큰을 통해 사용자를 식별합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserProfileController {

    private final UserProfileService profileService;

    /**
     * 인증된 사용자의 프로필 정보를 조회합니다.
     * <p>
     * JWT 토큰을 통해 인증된 사용자의 정보를 반환합니다.
     * </p>
     *
     * @param authentication Spring Security 인증 정보 (JWT 필터에서 설정)
     * @return 사용자 프로필 정보
     */
    @GetMapping("/me")
    public UserResponse getMyProfile(Authentication authentication) {

        // jwt 필터가 인증 완료한 사용자 아이디 추출
        String email = authentication.getName();

        log.debug("getMyProfile email: {}", email);

        return profileService.getUserProfileByEmail(email);
    }

    /**
     * 특정 사용자의 공개 프로필 정보를 조회합니다.
     * <p>
     * 다른 사용자의 공개 정보를 조회할 때 사용합니다.
     * </p>
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자 공개 프로필 정보
     */
    @GetMapping("/users/{userId}")
    public UserResponse getUserProfile(@PathVariable Long userId) {
        return profileService.getUserProfileById(userId);
    }
}
