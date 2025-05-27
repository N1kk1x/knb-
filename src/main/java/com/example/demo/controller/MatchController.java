package com.example.demo.controller;

import com.example.demo.dto.MatchRequest;
import com.example.demo.dto.MatchResponse;
import com.example.demo.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.MatchService;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    @Autowired private MatchService matchService;

    @PostMapping
    public ResponseEntity<MatchResponse> record(@RequestBody MatchRequest req) {
        Match m = new Match();
        m.setPlayer1Id(req.getPlayer1Id());
        m.setPlayer2Id(req.getPlayer2Id());
        m.setPlayer1Choice(req.getPlayer1Choice());
        m.setPlayer2Choice(req.getPlayer2Choice());
        m.setWinnerId(req.getWinnerId());
        Match saved = matchService.recordMatch(m);
        return ResponseEntity.ok(new MatchResponse(saved.getId(), saved.getWinnerId(), saved.getPlayedAt()));
    }
}
