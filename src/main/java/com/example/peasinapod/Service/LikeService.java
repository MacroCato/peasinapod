package com.example.peasinapod.Service;

import com.example.peasinapod.Common.Like;
import com.example.peasinapod.Common.Profile;
import com.example.peasinapod.Common.User;
import com.example.peasinapod.Repository.LikeRepository;
import com.example.peasinapod.Repository.ProfileRepository;
import com.example.peasinapod.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    public Like likeProfile(Long userId, Long profileId) {
        logger.debug("LikeService: Attempting to like profile. UserId: {}, ProfileId: {}", userId, profileId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("LikeService: User not found. UserId: {}", userId);
            return new IllegalArgumentException("User not found");
        });

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> {
            logger.error("LikeService: Profile not found. ProfileId: {}", profileId);
            return new IllegalArgumentException("Profile not found");
        });

        Like like = new Like();
        like.setUser(user);
        like.setProfile(profile);

        Like savedLike = likeRepository.save(like);
        logger.info("LikeService: Profile liked. LikeId: {}", savedLike.getId());

        return savedLike;
    }

    public List<Like> getLikesByUser(Long userId) {
        logger.debug("LikeService: Fetching likes for user. UserId: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("LikeService: User not found. UserId: {}", userId);
            return new IllegalArgumentException("User not found");
        });

        return likeRepository.findByUser(user);
    }
}