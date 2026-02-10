package com.projedata.autoflex.inventory.dtos.auth;

import com.projedata.autoflex.inventory.entities.enums.UserRole;

public record UserSummaryResponse(
        Long id,
        String name,
        String email,
        UserRole role
) {}