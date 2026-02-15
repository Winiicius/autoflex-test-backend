package com.projedata.autoflex.inventory.controllers;

import com.projedata.autoflex.inventory.dtos.auth.LoginRequest;
import com.projedata.autoflex.inventory.dtos.auth.LoginResponse;
import com.projedata.autoflex.inventory.dtos.auth.RegisterRequest;
import com.projedata.autoflex.inventory.dtos.auth.RegisterResponse;
import com.projedata.autoflex.inventory.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }
}