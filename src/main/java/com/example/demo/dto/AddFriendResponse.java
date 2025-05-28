package com.example.demo.dto;

public class AddFriendResponse {
    private String message;
    private String addedFriendName;

    public AddFriendResponse(String message, String addedFriendName) {
        this.message = message;
        this.addedFriendName = addedFriendName;
    }

    // Геттеры и сеттеры
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddedFriendName() {
        return addedFriendName;
    }

    public void setAddedFriendName(String addedFriendName) {
        this.addedFriendName = addedFriendName;
    }
}
