package com.example.demo.controller;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping
    public ResponseEntity<List<Friend>> getFriends() {
        // Получаем текущего пользователя (например, из сессии или токена)
        User currentUser = getCurrentUser();

        // Получаем список друзей текущего пользователя
        List<Friend> friends = friendService.getFriendsByUserId(currentUser.getId());

        return ResponseEntity.ok(friends);
    }

    private User getCurrentUser() {
        // Логика получения текущего пользователя
        // Например, из сессии или токена
    }
}
