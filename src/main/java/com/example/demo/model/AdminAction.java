package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adminactions")
public class AdminAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "admin_id", nullable = false)
    private Integer adminId;

    @Column(name = "target_user_id", nullable = false)
    private Integer targetUserId;

    @Column(columnDefinition = "ENUM('edit','block','unblock')")
    private String action;

    @Column(name = "action_info", columnDefinition = "TEXT")
    private String actionInfo;

    @Column(name = "action_time")
    private LocalDateTime actionTime;

    // Геттеры и сеттеры

    public Integer getId() { return id; }
    public Integer getAdminId() { return adminId; }
    public Integer getTargetUserId() { return targetUserId; }
    public String getAction() { return action; }
    public String getActionInfo() { return actionInfo; }
    public LocalDateTime getActionTime() { return actionTime; }

    public void setAdminId(Integer adminId) { this.adminId = adminId; }
    public void setTargetUserId(Integer targetUserId) { this.targetUserId = targetUserId; }
    public void setAction(String action) { this.action = action; }
    public void setActionInfo(String actionInfo) { this.actionInfo = actionInfo; }
    public void setActionTime(LocalDateTime actionTime) { this.actionTime = actionTime; }
}
