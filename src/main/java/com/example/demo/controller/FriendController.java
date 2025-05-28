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

    @PostMapping("/add")
    public ResponseEntity<?> addFriend(@RequestBody AddFriendRequest request, Principal principal) {
        String username = principal.getName();
        try {
            friendService.addFriend(username, request.getFriendName());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Friend>> getFriends(Principal principal) {
        String username = principal.getName();
        List<Friend> friends = friendService.getFriendsByUsername(username);
        return ResponseEntity.ok(friends);
    }
}

