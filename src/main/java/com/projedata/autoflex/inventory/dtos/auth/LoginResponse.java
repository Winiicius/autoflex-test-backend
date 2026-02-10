package com.projedata.autoflex.inventory.dtos.auth;

public record LoginResponse(
        String token,
        UserSummaryResponse user
) {}