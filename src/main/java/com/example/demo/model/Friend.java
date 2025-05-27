package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "friend_id", nullable = false)
    private Integer friendId;

    @Column(columnDefinition = "ENUM('pending','accepted','rejected') DEFAULT 'pending'")
    private String status;

    // Геттеры и сеттеры

    public Integer getId() { return id; }
    public Integer getUserId() { return userId; }
    public Integer getFriendId() { return friendId; }
    public String getStatus() { return status; }

    public void setUserId(Integer userId) { this.userId = userId; }
    public void setFriendId(Integer friendId) { this.friendId = friendId; }
    public void setStatus(String status) { this.status = status; }
}
