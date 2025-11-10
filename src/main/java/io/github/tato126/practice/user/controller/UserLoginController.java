package io.github.tato126.practice.user.controller;

import io.github.tato126.practice.user.dto.request.UserRequest;
import io.github.tato126.practice.user.dto.response.LoginResponse;
import io.github.tato126.practice.user.dto.response.UserResponse;
import io.github.tato126.practice.user.service.UserLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class UserLoginController {

    private final UserLoginService userService;

    // signup
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public UserResponse signup(@RequestBody UserRequest request) {
        return userService.signup(request);
    }

    // login
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody UserRequest request) {
        return userService.login(request);
    }

}
