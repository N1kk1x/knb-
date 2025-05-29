package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EloRatingService {

    private static final int K_FACTOR = 32;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateRatings(Integer winnerId, Integer loserId) {
        User winner = userRepository.findById(winnerId)
                .orElseThrow(() -> new IllegalArgumentException("Победитель не найден"));

        User loser = userRepository.findById(loserId)
                .orElseThrow(() -> new IllegalArgumentException("Проигравший не найден"));

        updateRatings(winner, loser);
    }

    @Transactional
    public void updateRatingsByUsername(String winnerUsername, String loserUsername) {
        User winner = userRepository.findByUsername(winnerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь-победитель не найден: " + winnerUsername));

        User loser = userRepository.findByUsername(loserUsername)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь-проигравший не найден: " + loserUsername));

        updateRatings(winner, loser);
    }

    private void updateRatings(User winner, User loser) {
        int winnerRating = winner.getEloRating();
        int loserRating = loser.getEloRating();

        double expectedWinner = expectedScore(winnerRating, loserRating);
        double expectedLoser = expectedScore(loserRating, winnerRating);

        int updatedWinnerRating = calculateNewRating(winnerRating, 1, expectedWinner);
        int updatedLoserRating = calculateNewRating(loserRating, 0, expectedLoser);

        winner.setEloRating(updatedWinnerRating);
        loser.setEloRating(updatedLoserRating);

        userRepository.save(winner);
        userRepository.save(loser);
    }

    private double expectedScore(int ratingA, int ratingB) {
        return 1.0 / (1.0 + Math.pow(10, (ratingB - ratingA) / 400.0));
    }

    private int calculateNewRating(int currentRating, int actualScore, double expectedScore) {
        return (int) Math.round(currentRating + K_FACTOR * (actualScore - expectedScore));
    }
}
