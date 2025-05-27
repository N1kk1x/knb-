package com.example.demo.controller;

import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationRequest req) {
        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        u.setRole("user");
        u.setIsBlocked(false);
        u.setEloRating(1000);
        User saved = authService.register(u);
        return ResponseEntity.ok(new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest req) {
        return authService.login(req.getUsername(), req.getPassword())
                .map(u -> ResponseEntity.ok(new UserResponse(u.getId(), u.getUsername(), u.getEmail())))
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
