package com.example.demo.controller;

import com.example.demo.model.Friend;
import com.example.demo.model.User;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping
    public ResponseEntity<List<Friend>> getFriends(Principal principal) {
        // Получаем имя текущего пользователя из Principal
        String username = principal.getName();
        List<Friend> friends = friendService.getFriendsByUsername(username);
        return ResponseEntity.ok(friends);
    }
}
