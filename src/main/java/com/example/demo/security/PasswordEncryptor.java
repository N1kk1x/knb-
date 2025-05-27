package com.example.demo.security;

import com.example.demo.db_connection.DatabaseConnection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordEncryptor {
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncryptor(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void saveEncryptedPassword(String username, String rawPassword) throws SQLException {
        String encryptedPassword = encryptPassword(rawPassword);
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            statement.executeUpdate();
        }
    }
}
