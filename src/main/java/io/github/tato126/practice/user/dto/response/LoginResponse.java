package io.github.tato126.practice.user.dto.response;

import io.github.tato126.practice.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "로그인 성공 응답 DTO")
@Builder
public record LoginResponse(

        @Schema(description = "사용자 정보")
        UserResponse user,

        @Schema(
                description = "JWT 액세스 토큰 (Bearer 토큰)",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        )
        String accessToken
) {

    /**
     * User 엔티티와 JWT 토큰으로 LoginResponse를 생성합니다.
     *
     * @param user        User 엔티티
     * @param accessToken JWT 액세스 토큰
     * @return LoginResponse DTO
     */
    public static LoginResponse form(User user, String accessToken) {
        return LoginResponse.builder()
                .user(UserResponse.form(user))
                .accessToken(accessToken)
                .build();
    }
}
