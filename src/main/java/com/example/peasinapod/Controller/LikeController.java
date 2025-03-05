package com.example.peasinapod.Controller;

import com.example.peasinapod.Common.Like;
import com.example.peasinapod.Service.LikeService;
import com.example.peasinapod.DTO.LikeData;
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
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    @PostMapping
    public ResponseEntity<Like> likeProfile(@RequestBody LikeData likeData) {
        try {
            logger.debug("LikeController: Attempting to like profile.");
            Like like = likeService.likeProfile(likeData.getUserId(), likeData.getProfileId());
            return new ResponseEntity<>(like, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Like>> getLikesByUser(@PathVariable Long userId) {
        try {
            List<Like> likes = likeService.getLikesByUser(userId);
            return new ResponseEntity<>(likes, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}