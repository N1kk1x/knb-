package com.example.demo.dto;

import com.example.demo.model.User;

public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private Integer eloRating;
    private Integer matchesPlayed;
    private Integer wins;

    public UserResponse(User user) {
        this.id = user.getId();   // Integer
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.eloRating = user.getEloRating();
        this.matchesPlayed = user.getMatchesPlayed();
        this.wins = user.getWins();
    }

    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Integer getEloRating() { return eloRating; }
    public Integer getMatchesPlayed() { return matchesPlayed; }
    public Integer getWins() { return wins; }
}
