package com.example.Ehpad.controller;

import com.example.Ehpad.dto.LoginRequest;
import com.example.Ehpad.dto.LoginResponse;
import com.example.Ehpad.entity.AppUser;
import com.example.Ehpad.repository.AppUserRepository;
import com.example.Ehpad.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            AppUser user = appUserRepository.findByUsername(request.getUsername())
                    .orElseThrow();

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            return ResponseEntity.ok(LoginResponse.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .username(user.getUsername())
                    .role(user.getRole())
                    .nom(user.getNom())
                    .prenom(user.getPrenom())
                    .expiresIn(expiration)
                    .build());

        } catch (BadCredentialsException e) {
            log.warn("Tentative de connexion échouée pour: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        AppUser user = appUserRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(LoginResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .build());
    }
}
