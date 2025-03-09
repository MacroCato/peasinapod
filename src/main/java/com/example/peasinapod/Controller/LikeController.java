package com.example.peasinapod.Controller;

import com.example.peasinapod.Data.Common.Like;
import com.example.peasinapod.Service.LikeService;
import com.example.peasinapod.Data.DTO.LikeDTO;
import com.example.peasinapod.Data.DTO.ProfileDTO;
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

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @PostMapping
    @CrossOrigin
    public ResponseEntity<LikeDTO> likeProfile(@RequestBody LikeDTO likeDTO) {
        try {
  //          logger.debug("LikeController: Attempting to like profile with userId: {} and profileId: {}", LikeDTO.getUserId(), likeDTO.getProfileId());
            LikeDTO newlikeDTO = likeService.likeProfile(likeDTO.getUserId(), likeDTO.getProfileId());
            //LikeDTO newlikeDTO = likeAdapter.convertToDTO(like);
            return new ResponseEntity<>(newlikeDTO, HttpStatus.CREATED);
            //return new ResponseEntity<>(like, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("LikeController: Error liking profile - {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProfileDTO>> getLikesByUser(@PathVariable Long userId) {
        try {
            logger.debug("LikeController: Fetching likes for user. UserId: {}", userId);
            List<ProfileDTO> likes = likeService.getLikesByUser(userId);
            return new ResponseEntity<>(likes, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("LikeController: Error fetching likes for user - {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unlikeProfile(@RequestBody LikeDTO likeDTO) {
        try {
            logger.debug("LikeController: Attempting to unlike profile with userId: {} and profileId: {}", likeDTO.getUserId(), likeDTO.getProfileId());
            likeService.unlikeProfile(likeDTO.getUserId(), likeDTO.getProfileId());
            return new ResponseEntity<>("Profile unliked successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("LikeController: Error unliking profile - {}", e.getMessage());
            return new ResponseEntity<>("Error unliking profile: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}