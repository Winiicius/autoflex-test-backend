package com.projedata.autoflex.inventory.services;

import com.projedata.autoflex.inventory.dtos.auth.*;
import com.projedata.autoflex.inventory.entities.User;
import com.projedata.autoflex.inventory.entities.enums.UserRole;
import com.projedata.autoflex.inventory.exceptions.ConflictException;
import com.projedata.autoflex.inventory.exceptions.ResourceNotFoundException;
import com.projedata.autoflex.inventory.mappers.UserMapper;
import com.projedata.autoflex.inventory.repositories.UserRepository;
import com.projedata.autoflex.inventory.security.JwtService;
import com.projedata.autoflex.inventory.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private UserMapper userMapper;

    @InjectMocks private AuthServiceImpl service;

    @Test
    void register_shouldCreateUserWithUserRoleAndActiveTrue_andReturnResponse() {
        RegisterRequest req = new RegisterRequest(
                "John Doe",
                "  John@Doe.com  ",
                "123456"
        );

        when(userRepository.existsByEmail("john@doe.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hashed");

        User saved = new User();
        saved.setId(10L);
        saved.setName("John Doe");
        saved.setEmail("john@doe.com");
        saved.setPassword("hashed");
        saved.setRole(UserRole.USER);
        saved.setActive(true);

        when(userRepository.save(any(User.class))).thenReturn(saved);

        RegisterResponse expected = new RegisterResponse(
                10L, "John Doe", "john@doe.com", UserRole.USER, true
        );
        when(userMapper.toRegisterResponse(saved)).thenReturn(expected);

        RegisterResponse resp = service.register(req);

        assertThat(resp).isEqualTo(expected);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User toSave = userCaptor.getValue();
        assertThat(toSave.getName()).isEqualTo("John Doe");
        assertThat(toSave.getEmail()).isEqualTo("john@doe.com");
        assertThat(toSave.getPassword()).isEqualTo("hashed");
        assertThat(toSave.getRole()).isEqualTo(UserRole.USER);
        assertThat(toSave.isActive()).isTrue();

        verify(userRepository).existsByEmail("john@doe.com");
        verify(passwordEncoder).encode("123456");
        verify(userMapper).toRegisterResponse(saved);
    }

    @Test
    void register_shouldThrowConflict_whenEmailAlreadyExists() {
        RegisterRequest req = new RegisterRequest("John", "john@doe.com", "123456");

        when(userRepository.existsByEmail("john@doe.com")).thenReturn(true);

        assertThatThrownBy(() -> service.register(req))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("Email already exists");

        verify(userRepository).existsByEmail("john@doe.com");
        verifyNoInteractions(passwordEncoder, jwtService, userMapper);
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldReturnTokenAndUser_whenCredentialsAreValid() {
        LoginRequest req = new LoginRequest("admin@autoflex.com", "123456");

        User user = new User();
        user.setId(1L);
        user.setName("Autoflex Admin");
        user.setEmail("admin@autoflex.com");
        user.setPassword("hashed");
        user.setRole(UserRole.ADMIN);
        user.setActive(true);

        when(userRepository.findByEmail("admin@autoflex.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "hashed")).thenReturn(true);

        when(jwtService.generateToken(eq("admin@autoflex.com"), anyMap()))
                .thenReturn("jwt-token");

        var summary = new UserSummaryResponse(
                1L, "Autoflex Admin", "admin@autoflex.com", UserRole.ADMIN
        );
        when(userMapper.toSummary(user)).thenReturn(summary);

        LoginResponse resp = service.login(req);

        assertThat(resp.token()).isEqualTo("jwt-token");
        assertThat(resp.user()).isNotNull();
        assertThat(resp.user().id()).isEqualTo(1L);
        assertThat(resp.user().name()).isEqualTo("Autoflex Admin");
        assertThat(resp.user().email()).isEqualTo("admin@autoflex.com");
        assertThat(resp.user().role()).isEqualTo(UserRole.ADMIN);

        verify(userRepository).findByEmail("admin@autoflex.com");
        verify(passwordEncoder).matches("123456", "hashed");
        verify(userMapper).toSummary(user);
    }


    @Test
    void login_shouldThrowNotFound_whenUserDoesNotExist() {
        LoginRequest req = new LoginRequest("noone@autoflex.com", "123456");

        when(userRepository.findByEmail("noone@autoflex.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.login(req))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findByEmail("noone@autoflex.com");
        verifyNoInteractions(passwordEncoder, jwtService, userMapper);
    }
}
