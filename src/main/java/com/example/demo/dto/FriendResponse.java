package com.example.demo.dto;

import com.example.demo.model.Friend;

public class FriendResponse {
    private Integer id;
    private Integer userId;
    private Integer friendId;
    private String status;

    public FriendResponse(Friend friend) {
        this.id = friend.getId();
        this.userId = friend.getUserId();
        this.friendId = friend.getFriendId();
        this.status = friend.getStatus();
    }

    // Геттеры

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public String getStatus() {
        return status;
    }
}
