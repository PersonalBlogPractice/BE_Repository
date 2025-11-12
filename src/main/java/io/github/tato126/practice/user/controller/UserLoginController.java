package io.github.tato126.practice.user.controller;

import io.github.tato126.practice.common.dto.ErrorResponse;
import io.github.tato126.practice.user.dto.request.UserRequest;
import io.github.tato126.practice.user.dto.response.LoginResponse;
import io.github.tato126.practice.user.dto.response.UserResponse;
import io.github.tato126.practice.user.service.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 인증 관련 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * <p>
 * 회원가입, 로그인 등의 REST API 엔드포인트를 제공하며,
 * JWT 토큰 발급 기능을 담당합니다.
 * </p>
 *
 * @author tato126
 * @since 1.0
 */
@Tag(name = "Auth", description = "인증 API - 회원가입, 로그인")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserLoginController {

    private final UserLoginService userService;

    @Operation(
            summary = "회원가입",
            description = "새로운 사용자를 등록합니다. 이메일은 고유해야 하며, 비밀번호는 암호화되어 저장됩니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공 - 새 사용자 생성됨",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "입력값 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 존재하는 이메일",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public UserResponse signup(@Valid @RequestBody UserRequest request) {
        return userService.signup(request);
    }

    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인하여 JWT 액세스 토큰을 발급받습니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "입력값 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "이메일 또는 비밀번호 불일치",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody UserRequest request) {
        return userService.login(request);
    }

}
