package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.auth.LoginRequest;
import com.projedata.autoflex.inventory.dtos.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
