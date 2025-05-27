package com.example.demo.service;

import com.example.demo.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.MatchRepository;

@Service
public class MatchService {
    @Autowired private MatchRepository matchRepository;

    public Match recordMatch(Match match) {
        return matchRepository.save(match);
    }
}
