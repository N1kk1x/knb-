package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.EmailService;
import com.example.demo.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());

        User savedUser = authService.register(newUser);

        // Генерация и отправка токена подтверждения
        String token = confirmationTokenService.generateToken(savedUser);
        emailService.sendConfirmationEmail(savedUser.getEmail(), token);

        return ResponseEntity.ok(new UserResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var userOptional = authService.login(request.getUsername(), request.getPassword());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(new UserResponse(user));
        } else {
            return ResponseEntity
                    .status(401)
                    .body("Ошибка входа: неверное имя пользователя или пароль");
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmToken(@RequestParam String token) {
        String result = confirmationTokenService.confirmToken(token);
        return ResponseEntity.ok(result);
    }
}
