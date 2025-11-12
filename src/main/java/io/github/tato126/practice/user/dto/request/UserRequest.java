package io.github.tato126.practice.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * 사용자 회원가입/로그인 요청 DTO입니다.
 * <p>
 * 클라이언트로부터 사용자 정보를 받아올 때 사용합니다.
 * 이메일, 비밀번호, 닉네임, 자기소개 정보를 포함하며,
 * 로그인 시에는 이메일과 비밀번호만 필수입니다.
 * </p>
 *
 * @param email    사용자 이메일 (필수, 이메일 형식)
 * @param password 비밀번호 (필수, 8자 이상)
 * @param nickname 닉네임 (선택)
 * @param bio      자기소개 (선택)
 * @author tato126
 * @since 1.0
 */
@Schema(description = "회원가입/로그인 요청 DTO")
@Builder
public record UserRequest(

        @Schema(
                description = "사용자 이메일 (고유값)",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Email(message = "이메일 형식이 올바르지 않습니다")
        @NotNull(message = "이메일은 필수입니다")
        String email,

        @Schema(
                description = "비밀번호 (8자 이상)",
                example = "password123!",
                minLength = 8,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다")
        @NotBlank(message = "비밀번호는 필수입니다")
        String password,

        @Schema(
                description = "사용자 닉네임",
                example = "개발자123",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String nickname,

        @Schema(
                description = "자기소개",
                example = "백엔드 개발자입니다.",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String bio
) {
}
