package io.github.tato126.practice.user.dto.response;

import io.github.tato126.practice.user.entity.User;
import lombok.Builder;

@Builder
public record LoginResponse(
        UserResponse user,
        String accessToken
) {

    public static LoginResponse form(User user, String accessToken) {
        return LoginResponse.builder()
                .user(UserResponse.form(user))
                .accessToken(accessToken)
                .build();
    }
}
