package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public class MatchRequest {

    @NotNull(message = "Player1 ID is required")
    private Integer player1Id;

    @NotNull(message = "Player2 ID is required")
    private Integer player2Id;

    private String player1Choice;
    private String player2Choice;

    // winnerId может быть null, если матч ещё не завершён
    private Integer winnerId;

    // Геттеры и сеттеры
    public Integer getPlayer1Id() { return player1Id; }
    public void setPlayer1Id(Integer player1Id) { this.player1Id = player1Id; }
    public Integer getPlayer2Id() { return player2Id; }
    public void setPlayer2Id(Integer player2Id) { this.player2Id = player2Id; }
    public String getPlayer1Choice() { return player1Choice; }
    public void setPlayer1Choice(String player1Choice) { this.player1Choice = player1Choice; }
    public String getPlayer2Choice() { return player2Choice; }
    public void setPlayer2Choice(String player2Choice) { this.player2Choice = player2Choice; }
    public Integer getWinnerId() { return winnerId; }
    public void setWinnerId(Integer winnerId) { this.winnerId = winnerId; }
}
