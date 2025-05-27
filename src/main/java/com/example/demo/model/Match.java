package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "player1_id", nullable = false)
    private Integer player1Id;

    @Column(name = "player2_id", nullable = false)
    private Integer player2Id;

    @Column(name = "player1_choice", columnDefinition = "ENUM('rock','paper','scissors')")
    private String player1Choice;

    @Column(name = "player2_choice", columnDefinition = "ENUM('rock','paper','scissors')")
    private String player2Choice;

    @Column(name = "winner_id")
    private Integer winnerId;

    @Column(name = "played_at")
    private LocalDateTime playedAt;

    // Геттеры и сеттеры

    public Integer getId() { return id; }
    public Integer getPlayer1Id() { return player1Id; }
    public Integer getPlayer2Id() { return player2Id; }
    public String getPlayer1Choice() { return player1Choice; }
    public String getPlayer2Choice() { return player2Choice; }
    public Integer getWinnerId() { return winnerId; }
    public LocalDateTime getPlayedAt() { return playedAt; }

    public void setPlayer1Id(Integer player1Id) { this.player1Id = player1Id; }
    public void setPlayer2Id(Integer player2Id) { this.player2Id = player2Id; }
    public void setPlayer1Choice(String player1Choice) { this.player1Choice = player1Choice; }
    public void setPlayer2Choice(String player2Choice) { this.player2Choice = player2Choice; }
    public void setWinnerId(Integer winnerId) { this.winnerId = winnerId; }
    public void setPlayedAt(LocalDateTime playedAt) { this.playedAt = playedAt; }
}
