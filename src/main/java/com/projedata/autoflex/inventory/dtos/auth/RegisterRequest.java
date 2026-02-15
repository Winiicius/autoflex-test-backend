package com.projedata.autoflex.inventory.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "name is required")
        @Size(max = 120, message = "name must be at most 120 characters")
        String name,

        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        @Size(max = 150, message = "email must be at most 150 characters")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 6, max = 255, message = "password must be between 6 and 255 characters")
        String password
) {}
