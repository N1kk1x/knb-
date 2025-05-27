package com.example.demo.dto;

import java.time.LocalDateTime;

public class MatchResponse {

    private Integer id;
    private Integer winnerId;
    private LocalDateTime playedAt;

    public MatchResponse() { }

    public MatchResponse(Integer id, Integer winnerId, LocalDateTime playedAt) {
        this.id = id;
        this.winnerId = winnerId;
        this.playedAt = playedAt;
    }

    // Геттеры и сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getWinnerId() { return winnerId; }
    public void setWinnerId(Integer winnerId) { this.winnerId = winnerId; }
    public LocalDateTime getPlayedAt() { return playedAt; }
    public void setPlayedAt(LocalDateTime playedAt) { this.playedAt = playedAt; }
}
