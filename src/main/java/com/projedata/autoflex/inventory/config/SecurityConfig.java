
package com.projedata.autoflex.inventory.config;

import com.projedata.autoflex.inventory.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll()

                        // Read-only (USER or ADMIN)
                        .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/raw-materials/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/production/**").hasAnyRole("USER", "ADMIN")

                        // Product write (ADMIN only)
                        .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                        // Raw material write (ADMIN only)
                        .requestMatchers(HttpMethod.POST, "/raw-materials/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/raw-materials/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/raw-materials/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Fallback
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

