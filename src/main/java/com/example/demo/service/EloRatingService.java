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

    /**
     * Обновляет рейтинги двух игроков после матча.
     *
     * @param winnerId ID победителя
     * @param loserId  ID проигравшего
     */
    @Transactional
    public void updateRatings(Integer winnerId, Integer loserId) {
        User winner = userRepository.findById(winnerId)
                .orElseThrow(() -> new IllegalArgumentException("Победитель не найден"));

        User loser = userRepository.findById(loserId)
                .orElseThrow(() -> new IllegalArgumentException("Проигравший не найден"));

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

    /**
     * Вычисляет ожидаемый результат на основе рейтингов.
     */
    private double expectedScore(int ratingA, int ratingB) {
        return 1.0 / (1.0 + Math.pow(10, (ratingB - ratingA) / 400.0));
    }

    /**
     * Вычисляет новый рейтинг игрока.
     *
     * @param currentRating   текущий рейтинг
     * @param actualScore     фактический результат (1 — победа, 0 — поражение)
     * @param expectedScore   ожидаемый результат
     * @return новый рейтинг
     */
    private int calculateNewRating(int currentRating, int actualScore, double expectedScore) {
        return (int) Math.round(currentRating + K_FACTOR * (actualScore - expectedScore));
    }
}
