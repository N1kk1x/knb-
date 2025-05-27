package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "ENUM('user','admin') DEFAULT 'user'")
    private String role;

    @Column(name = "is_blocked", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isBlocked;

    @Column(name = "elo_rating", columnDefinition = "INT DEFAULT 1000")
    private Integer eloRating;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public User() {
        this.eloRating = 1000;
        this.isBlocked = false;
        this.role = "user";
    }

    // Геттеры и сеттеры

    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public boolean getIsBlocked() { return isBlocked; }
    public Integer getEloRating() { return eloRating; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setIsBlocked(boolean isBlocked) { this.isBlocked = isBlocked; }
    public void setEloRating(Integer eloRating) { this.eloRating = eloRating; }
}
