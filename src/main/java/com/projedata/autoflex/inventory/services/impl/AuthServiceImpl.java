package com.projedata.autoflex.inventory.services.impl;

import com.projedata.autoflex.inventory.dtos.auth.*;
import com.projedata.autoflex.inventory.entities.User;
import com.projedata.autoflex.inventory.entities.enums.UserRole;
import com.projedata.autoflex.inventory.exceptions.ConflictException;
import com.projedata.autoflex.inventory.exceptions.ResourceNotFoundException;
import com.projedata.autoflex.inventory.exceptions.UnauthorizedException;
import com.projedata.autoflex.inventory.mappers.UserMapper;
import com.projedata.autoflex.inventory.repositories.UserRepository;
import com.projedata.autoflex.inventory.security.JwtService;
import com.projedata.autoflex.inventory.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private JwtService jwtService;

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

        String token = jwtService.generateToken(
                user.getEmail(),
                java.util.Map.of(
                        "role", user.getRole().name(),
                        "userId", user.getId()
                )
        );

        var userSummary = userMapper.toSummary(user);

        return new LoginResponse(token, userSummary);
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Email already exists: " + email);
        }

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.USER);
        user.setActive(true);

        User saved = userRepository.save(user);

        return userMapper.toRegisterResponse(saved);
    }
}
