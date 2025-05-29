package com.example.demo.model;

import java.util.List;

public class PlayerMove {
    private String username;
    private List<String> moves; // Список из 3 ходов: "rock", "paper", "scissors"
    private String gameId;

    public PlayerMove() {
    }

    public PlayerMove(String username, List<String> moves, String gameId) {
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

    public List<String> getMoves() {
        return moves;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
