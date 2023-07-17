package com.example.webshop.controllers;

import com.example.webshop.models.dto.LoginResponse;
import com.example.webshop.models.requests.LoginRequest;
import com.example.webshop.models.requests.SignUpRequest;
import com.example.webshop.services.AuthService;
import com.example.webshop.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    @PostMapping("login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request)
    {
        return authService.login(request);
    }
    @PostMapping("sign-up")
    public void signUp(@RequestBody @Valid SignUpRequest request)
    {
        userService.signUp(request);
    }


}
