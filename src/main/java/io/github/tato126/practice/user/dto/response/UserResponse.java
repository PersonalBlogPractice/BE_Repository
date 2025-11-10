package io.github.tato126.practice.user.dto.response;

import io.github.tato126.practice.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

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
