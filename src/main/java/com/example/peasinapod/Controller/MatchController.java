package com.example.peasinapod.Controller;

import com.example.peasinapod.Data.Common.Match;
import com.example.peasinapod.Service.MatchService;
import com.example.peasinapod.Data.DTO.MatchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);

    @GetMapping("/{userId}")
    public ResponseEntity<List<MatchResponse>> getMatchesByUser(@PathVariable Long userId) {
        try {
            logger.debug("MatchController: Fetching matches for userId: {}", userId);
            List<MatchResponse> matches = matchService.getMatchesByUser(userId);
            return new ResponseEntity<>(matches, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("MatchController: Error fetching matches for userId: {}", userId, e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}