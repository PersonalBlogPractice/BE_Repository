package io.github.tato126.practice.user.dto.response;

import io.github.tato126.practice.user.entity.User;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String email,
        String nickname,
        String bio
) {

    public static UserResponse form(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getUsername())
                .bio(user.getBio())
                .build();
    }
}
