package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;

    public GameController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Принимаем ход игрока
    @MessageMapping("/move")
    public void processMove(PlayerMove move) {
        // Здесь логика: сохранять ход, ждать ход соперника,
        // определить победителя, обновить рейтинг и отправить результат игрокам.

        // Для упрощения - сразу отправим ход обратно на /topic/game (должна быть реализована логика на клиенте)
        messagingTemplate.convertAndSend("/topic/game", move);
    }

    // Класс для передачи хода игрока
    public static class PlayerMove {
        private String username;
        private String move; // "rock", "paper", "scissors"

        // геттеры/сеттеры
    }
}
