package com.example.demo.service;

import com.example.demo.model.Friend;
import com.example.demo.model.User;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public void addFriend(String username, String friendName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));

        User friend = userRepository.findByUsername(friendName)
                .orElseThrow(() -> new IllegalArgumentException("Друг не найден: " + friendName));

        Friend friendEntity = new Friend();
        friendEntity.setUserId(user.getId());
        friendEntity.setFriendId(friend.getId());
        friendEntity.setStatus("pending");

        friendRepository.save(friendEntity);
    }

    public List<Friend> getFriendsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + username));
        return friendRepository.findByUserId(user.getId());
    }
}

