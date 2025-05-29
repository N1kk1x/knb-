package com.example.demo.controller;

import com.example.demo.service.EloRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GameController {

    private final SimpMessagingTemplate messagingTemplate;
    private final EloRatingService eloRatingService;

    // Хранение ходов игроков по gameId
    private final Map<String, PlayerMoves> moves = new HashMap<>();

    @Autowired
    public GameController(SimpMessagingTemplate messagingTemplate, EloRatingService eloRatingService) {
        this.messagingTemplate = messagingTemplate;
        this.eloRatingService = eloRatingService;
    }

    @MessageMapping("/play")
    public void handleMove(PlayerMove move) {
        String gameId = move.getGameId();

        PlayerMoves playerMoves = moves.getOrDefault(gameId, new PlayerMoves());

        // Сохраняем ход игрока
        playerMoves.addMove(move.getUsername(), move.getMoves()); // теперь move.getMoves() - список из 3 ходов

        if (playerMoves.isComplete()) {
            // Получаем имена игроков и их серии ходов
            String player1 = playerMoves.getPlayer1();
            String player2 = playerMoves.getPlayer2();

            String[] moves1 = playerMoves.getMoves1();
            String[] moves2 = playerMoves.getMoves2();

            // Подсчёт результатов серии из 3 ходов
            int player1Wins = 0;
            int player2Wins = 0;
            int draws = 0;

            for (int i = 0; i < 3; i++) {
                String m1 = moves1[i];
                String m2 = moves2[i];

                if (m1.equals(m2)) {
                    draws++;
                } else if (
                        (m1.equals("rock") && m2.equals("scissors")) ||
                                (m1.equals("paper") && m2.equals("rock")) ||
                                (m1.equals("scissors") && m2.equals("paper"))
                ) {
                    player1Wins++;
                } else {
                    player2Wins++;
                }
            }

            String result1;
            String result2;

            if (player1Wins > player2Wins) {
                result1 = "win";
                result2 = "lose";
            } else if (player2Wins > player1Wins) {
                result1 = "lose";
                result2 = "win";
            } else {
                result1 = result2 = "draw";
            }

            // Отправляем результат обоим игрокам, показывая их серии ходов соперника
            messagingTemplate.convertAndSend("/topic/game/" + gameId + "/" + player1,
                    new GameResult(result1, moves2));
            messagingTemplate.convertAndSend("/topic/game/" + gameId + "/" + player2,
                    new GameResult(result2, moves1));

            // Обновляем рейтинг Elo, если есть победитель
            if ("win".equals(result1)) {
                eloRatingService.updateRatingsByUsername(player1, player2);
            } else if ("win".equals(result2)) {
                eloRatingService.updateRatingsByUsername(player2, player1);
            }

            // Очищаем сохранённые ходы для новой серии
            moves.remove(gameId);

        } else {
            // Сохраняем промежуточное состояние
            moves.put(gameId, playerMoves);
        }
    }

    // Вспомогательный класс для хранения ходов двух игроков
    private static class PlayerMoves {
        private String player1;
        private String player2;
        private String[] moves1;
        private String[] moves2;

        public void addMove(String username, String[] moves) {
            if (player1 == null) {
                player1 = username;
                moves1 = moves;
            } else if (player2 == null && !username.equals(player1)) {
                player2 = username;
                moves2 = moves;
            }
        }

        public boolean isComplete() {
            return player1 != null && player2 != null && moves1 != null && moves2 != null;
        }

        public String getPlayer1() {
            return player1;
        }

        public String getPlayer2() {
            return player2;
        }

        public String[] getMoves1() {
            return moves1;
        }

        public String[] getMoves2() {
            return moves2;
        }
    }

    // DTO для получения ходов от клиента
    public static class PlayerMove {
        private String username;
        private String[] moves;  // массив из 3 ходов, например ["rock", "scissors", "paper"]
        private String gameId;

        public PlayerMove() {
        }

        public PlayerMove(String username, String[] moves, String gameId) {
            this.username = username;
            this.moves = moves;
            this.gameId = gameId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String[] getMoves() {
            return moves;
        }

        public void setMoves(String[] moves) {
            this.moves = moves;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }
    }

    // DTO для отправки результата клиенту
    public static class GameResult {
        private String result;
        private String[] opponentMoves;

        public GameResult() {
        }

        public GameResult(String result, String[] opponentMoves) {
            this.result = result;
            this.opponentMoves = opponentMoves;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String[] getOpponentMoves() {
            return opponentMoves;
        }

        public void setOpponentMoves(String[] opponentMoves) {
            this.opponentMoves = opponentMoves;
        }
    }
}
