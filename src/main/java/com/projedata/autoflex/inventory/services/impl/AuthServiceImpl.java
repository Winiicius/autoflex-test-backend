package com.projedata.autoflex.inventory.services.impl;

import com.projedata.autoflex.inventory.dtos.auth.LoginRequest;
import com.projedata.autoflex.inventory.dtos.auth.LoginResponse;
import com.projedata.autoflex.inventory.dtos.auth.UserSummaryResponse;
import com.projedata.autoflex.inventory.entities.User;
import com.projedata.autoflex.inventory.exceptions.ResourceNotFoundException;
import com.projedata.autoflex.inventory.exceptions.UnauthorizedException;
import com.projedata.autoflex.inventory.repositories.UserRepository;
import com.projedata.autoflex.inventory.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isActive()) {
            throw new UnauthorizedException("User is inactive");
        }

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!passwordMatches) {
            throw new UnauthorizedException("Invalid credentials");
        }

        // temporary
        String token = UUID.randomUUID().toString();

        var userSummary = new UserSummaryResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return new LoginResponse(token, userSummary);
    }
}
