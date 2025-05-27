package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public class AdminActionRequest {

    @NotNull(message = "Admin ID is required")
    private Integer adminId;

    @NotNull(message = "Target User ID is required")
    private Integer targetUserId;

    @NotNull(message = "Action is required")
    private String action;       // edit, block или unblock

    private String actionInfo;   // дополнительная информация по действию

    // Геттеры и сеттеры
    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }
    public Integer getTargetUserId() { return targetUserId; }
    public void setTargetUserId(Integer targetUserId) { this.targetUserId = targetUserId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getActionInfo() { return actionInfo; }
    public void setActionInfo(String actionInfo) { this.actionInfo = actionInfo; }
}
