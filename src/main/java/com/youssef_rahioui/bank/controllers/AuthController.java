package com.youssef_rahioui.bank.controllers;

import com.youssef_rahioui.bank.dtos.LoginRequestDTO;
import com.youssef_rahioui.bank.dtos.LoginResponseDTO;
import com.youssef_rahioui.bank.dtos.RegisterRequestDTO;
import com.youssef_rahioui.bank.entities.AppRole;
import com.youssef_rahioui.bank.entities.AppUser;
import com.youssef_rahioui.bank.repositories.AppRoleRepository;
import com.youssef_rahioui.bank.repositories.AppUserRepository;
import com.youssef_rahioui.bank.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        AppUser user = appUserRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        String token = jwtTokenProvider.generateToken(user);
        return ResponseEntity.ok(new LoginResponseDTO(token, user.getUsername(), user.getEmail(), user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        if (appUserRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }

        AppRole userRole = appRoleRepository.findByRoleName("ROLE_USER")
                .orElseGet(() -> appRoleRepository.save(new AppRole(null, "ROLE_USER", "Default user role")));

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(List.of(userRole));

        AppUser saved = appUserRepository.save(user);
        String token = jwtTokenProvider.generateToken(saved);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LoginResponseDTO(token, saved.getUsername(), saved.getEmail(), saved.getId()));
    }
}
