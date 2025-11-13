package io.github.tato126.practice.user.controller;

import io.github.tato126.practice.common.dto.ErrorResponse;
import io.github.tato126.practice.user.dto.response.UserResponse;
import io.github.tato126.practice.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "프로필", description = "사용자 프로필 조회 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserProfileController {

    private final UserProfileService profileService;

    /**
     * 인증된 사용자의 프로필 정보를 조회합니다.
     * <p>
     * JWT 토큰을 통해 인증된 사용자의 정보를 반환합니다.
     * </p>
     *
     * @param userEmail JWT 토큰에서 추출된 사용자 이메일
     * @return 사용자 프로필 정보
     */
    @Operation(
            summary = "내 프로필 조회",
            description = "JWT 토큰으로 인증된 사용자의 프로필 정보를 조회합니다. Authorization 헤더에 Bearer 토큰 필요."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 - 유효하지 않은 토큰",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/auth/me")
    public UserResponse getMyProfile(
            @Parameter(hidden = true) @AuthenticationPrincipal String userEmail
    ) {
        log.debug("getMyProfile email: {}", userEmail);

        return profileService.getUserProfileByEmail(userEmail);
    }

    /**
     * 특정 사용자의 공개 프로필 정보를 조회합니다.
     * <p>
     * 다른 사용자의 공개 정보를 조회할 때 사용합니다. 인증 불필요.
     * </p>
     *
     * @param userId 조회할 사용자의 ID
     * @return 사용자 공개 프로필 정보
     */
    @Operation(
            summary = "사용자 프로필 조회",
            description = "특정 사용자의 공개 프로필 정보를 조회합니다. 인증 불필요."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자를 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/users/{userId}")
    public UserResponse getUserProfile(
            @Parameter(description = "조회할 사용자 ID", required = true, example = "1")
            @PathVariable Long userId
    ) {
        return profileService.getUserProfileById(userId);
    }
}
