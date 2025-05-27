package com.example.demo.dto;

import java.time.LocalDateTime;

public class AdminActionResponse {

    private Integer id;
    private String action;
    private LocalDateTime actionTime;

    public AdminActionResponse() { }

    public AdminActionResponse(Integer id, String action, LocalDateTime actionTime) {
        this.id = id;
        this.action = action;
        this.actionTime = actionTime;
    }

    // Геттеры и сеттеры
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public LocalDateTime getActionTime() { return actionTime; }
    public void setActionTime(LocalDateTime actionTime) { this.actionTime = actionTime; }
}
