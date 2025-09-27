package com.hazalyarimdunya.auth_jwt_app.controller;

import com.hazalyarimdunya.auth_jwt_app.dto.AuthRequest;
import com.hazalyarimdunya.auth_jwt_app.dto.AuthResponse;
import com.hazalyarimdunya.auth_jwt_app.dto.RegisterRequest;
import com.hazalyarimdunya.auth_jwt_app.entity.UserEntity;
import com.hazalyarimdunya.auth_jwt_app.services.JwtService;
import com.hazalyarimdunya.auth_jwt_app.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    // "/auth/register" endpointi ile register işlemini expose ediyoruz
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){ //Validation ile boş username/password engelleniyor
        UserEntity userEntity = userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getRole()
                );
        return ResponseEntity.ok("User registered successfully "+ userEntity.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        UserEntity userEntity = userService.getByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Password kontrolü
        if (!userService.checkPassword(request.getPassword(), userEntity.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Token üret
        String token = jwtService.generateToken(userEntity);
        return ResponseEntity.ok(new AuthResponse(token));
    }



}
