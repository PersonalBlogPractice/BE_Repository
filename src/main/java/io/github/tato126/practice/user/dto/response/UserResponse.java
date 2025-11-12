package io.github.tato126.practice.user.dto.response;

import io.github.tato126.practice.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * 사용자 정보 응답 DTO입니다.
 * <p>
 * 클라이언트에게 사용자 정보를 전달할 때 사용합니다.
 * 보안을 위해 비밀번호는 제외되며, 공개 프로필 정보만 포함됩니다.
 * </p>
 *
 * @param id       사용자 ID
 * @param email    이메일
 * @param nickname 닉네임
 * @param bio      자기소개
 * @author tato126
 * @since 1.0
 */
@Schema(description = "사용자 정보 응답 DTO")
@Builder
public record UserResponse(

        @Schema(description = "사용자 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "user@example.com")
        String email,

        @Schema(description = "닉네임", example = "개발자123")
        String nickname,

        @Schema(description = "자기소개", example = "백엔드 개발자입니다.")
        String bio
) {

    /**
     * User 엔티티를 UserResponse DTO로 변환합니다.
     *
     * @param user User 엔티티
     * @return UserResponse DTO
     */
    public static UserResponse form(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getUsername())
                .bio(user.getBio())
                .build();
    }
}
