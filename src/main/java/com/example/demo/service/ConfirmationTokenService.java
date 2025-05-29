package com.example.demo.service;

import com.example.demo.model.ConfirmationToken;
import com.example.demo.model.User;
import com.example.demo.repository.ConfirmationTokenRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setUser(user);
        confirmationTokenRepository.save(confirmationToken);
        return token;
    }

    public String confirmToken(String token) {
        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);

        if (confirmationTokenOptional.isEmpty()) {
            return "Токен не найден";
        }

        ConfirmationToken confirmationToken = confirmationTokenOptional.get();

        if (confirmationToken.getConfirmedAt() != null) {
            return "Email уже подтвержден";
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Токен истек";
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);

        User user = confirmationToken.getUser();
        user.setEnabled(true); // Предполагается, что у вас есть поле enabled в модели User
        userRepository.save(user);

        return "Email подтвержден успешно";
    }
}
