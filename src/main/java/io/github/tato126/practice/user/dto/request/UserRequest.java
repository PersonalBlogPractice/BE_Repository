package io.github.tato126.practice.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRequest(

        @Email @NotNull String email,
        String password,
        String nickname,
        String bio
) {
}
