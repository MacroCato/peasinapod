package com.example.peasinapod.Service;

import com.example.peasinapod.Data.Common.Like;
import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Data.DTO.LikeDTO;
import com.example.peasinapod.Repository.LikeRepository;
import com.example.peasinapod.Repository.ProfileRepository;
import com.example.peasinapod.Repository.UserRepository;
import com.example.peasinapod.Data.Common.Match;
import com.example.peasinapod.Repository.MatchRepository;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import com.example.peasinapod.Data.Adapter.LikeAdapter;
import com.example.peasinapod.Data.Adapter.ProfileAdapter;
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
    private MatchRepository matchRepository;

    @Autowired
    private LikeAdapter likeAdapter;

    @Autowired
    private ProfileAdapter profileAdapter;

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

            Profile currentUserProfile = profileRepository.findByUserId(userId).orElseThrow(() -> {
                logger.error("LikeService: Profile not found. UserId: {}", userId);
                return new IllegalArgumentException("Profile not found");
            });

            User likedUser = profile.getUser();

            // Check if the other user has already liked back
            Optional<Like> reciprocalLike = likeRepository.findByUserIdAndProfileId(likedUser.getId(), currentUserProfile.getId());
            if (reciprocalLike.isPresent()) {
                // Create a match
                Match match = new Match();
                match.setUser1(user);
                match.setUser2(profile.getUser());
                matchRepository.save(match);
                logger.info("LikeService: Match created. UserId: {}, ProfileId: {}", userId, profileId);
            }

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

            Optional<Match> existingMatch = matchRepository.findByUser1AndUser2(user, profile.getUser());
            if (existingMatch.isPresent()) {
                matchRepository.delete(existingMatch.get());
                logger.info("LikeService: Match deleted. User1Id: {}, User2Id: {}", user.getId(), profile.getUser().getId());
            }
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

        logger.debug("LikeService: Fetched user. UserId: {}", user.getId());
        List<Like> likes = likeRepository.findByUser(user);
        return likes.stream()
                    .map(like -> profileAdapter.convertToDTO(like.getProfile()))
                    .collect(Collectors.toList());
    }

    public List<ProfileDTO> getUsersWhoLikedUser(Long userId) {
        logger.debug("LikeService: Fetching users who liked user. UserId: {}", userId);

        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> {
            logger.error("LikeService: Profile not found. UserId: {}", userId);
            return new IllegalArgumentException("Profile not found");
        });

        logger.debug("LikeService: Attempting to get likes for profile id: {}", profile.getId());
        List<Like> likes = likeRepository.findByProfile(profile.getId());
        logger.debug("LikeService: Likes found for profile: {}", likes);

        return likes.stream()
                    .map(like -> {
                        User user = like.getUser();
                        logger.debug("LikeService: User found: {}", user);
                        Profile likedByProfile = profileRepository.findByUserId(user.getId()).orElseThrow(() -> {
                            logger.error("LikeService: Profile not found. UserId: {}", user.getId());
                            return new IllegalArgumentException("Profile not found");
                        });
                        ProfileDTO profileDTO = profileAdapter.convertToDTO(likedByProfile);
                        logger.debug("LikeService: Converted ProfileDTO: {}", profileDTO);
                        return profileDTO;
                    })
                    .collect(Collectors.toList());
    }
}