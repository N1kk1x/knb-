package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/game.html")
    public String gamePage() {
        return "game";
    }
/*
    @GetMapping("/main.html")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/profile.html")
    public String profilePage() {
        return "profile";
    }

    @GetMapping("/about.html")
    public String aboutPage() {
        return "about";
    } */
}
