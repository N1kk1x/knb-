package com.example.game_db.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private Long userId;
}