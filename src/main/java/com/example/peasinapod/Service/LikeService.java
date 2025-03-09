package com.example.peasinapod.Service;

import com.example.peasinapod.Data.Common.Like;
import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Data.DTO.LikeDTO;
import com.example.peasinapod.Repository.LikeRepository;
import com.example.peasinapod.Repository.ProfileRepository;
import com.example.peasinapod.Repository.UserRepository;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import com.example.peasinapod.Data.Adapter.LikeAdapter;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private LikeAdapter likeAdapter;

    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    public LikeDTO likeProfile(Long userId, Long profileId) {
        logger.debug("LikeService: Attempting to like profile. UserId: {}, ProfileId: {}", userId, profileId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("LikeService: User not found. UserId: {}", userId);
            return new IllegalArgumentException("User not found");
        });

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> {
            logger.error("LikeService: Profile not found. ProfileId: {}", profileId);
            return new IllegalArgumentException("Profile not found");
        });

        Optional<Like> existingLike = likeRepository.findByUserIdAndProfileId(userId, profileId);

        if (existingLike.isPresent()) {
            logger.error("LikeService: User already liked profile. UserId: {}, ProfileId: {}", userId, profileId);
            throw new IllegalArgumentException("User already liked profile");
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setProfile(profile);

            Like savedLike = likeRepository.save(like);
            logger.info("LikeService: Profile liked. LikeId: {}", savedLike.getId());

            LikeDTO savedLikeDTO = likeAdapter.convertToDTO(savedLike);
        return savedLikeDTO;
        }
    }

    public void unlikeProfile(Long userId, Long profileId) {
        logger.debug("LikeService: Attempting to unlike profile. UserId: {}, ProfileId: {}", userId, profileId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("LikeService: User not found. UserId: {}", userId);
            return new IllegalArgumentException("User not found");
        });

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> {
            logger.error("LikeService: Profile not found. ProfileId: {}", profileId);
            return new IllegalArgumentException("Profile not found");
        });

        Optional<Like> existingLike = likeRepository.findByUserIdAndProfileId(userId, profileId);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            logger.info("LikeService: Profile unliked. UserId: {}, ProfileId: {}", userId, profileId);
        } else {
            logger.error("LikeService: Like not found. UserId: {}, ProfileId: {}", userId, profileId);
            throw new IllegalArgumentException("Like not found");
        }
    }

    public List<ProfileDTO> getLikesByUser(Long userId) {
        logger.debug("LikeService: Fetching likes for user. UserId: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("LikeService: User not found. UserId: {}", userId);
            return new IllegalArgumentException("User not found");
        });

        List<Like> likes = likeRepository.findByUser(user);
        return likes.stream()
                    .map(like -> {
                        Profile profile = like.getProfile();
                        ProfileDTO profileDTO = new ProfileDTO();
                        profileDTO.setId(profile.getId());
                        profileDTO.setNickname(profile.getNickname());
                        profileDTO.setSummary(profile.getSummary());
                        return profileDTO;
                    })
                    .collect(Collectors.toList());
    }
}