package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.auth.LoginRequest;
import com.projedata.autoflex.inventory.dtos.auth.LoginResponse;
import com.projedata.autoflex.inventory.dtos.auth.RegisterRequest;
import com.projedata.autoflex.inventory.dtos.auth.RegisterResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
}
