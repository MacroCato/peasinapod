package com.example.peasinapod.Service;

import com.example.peasinapod.Data.Common.Match;
import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Repository.MatchRepository;
import com.example.peasinapod.Repository.ProfileRepository;
import com.example.peasinapod.Repository.UserRepository;
import com.example.peasinapod.Data.DTO.MatchResponse;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);

    public List<MatchResponse> getMatchesByUser(Long userId) {
        logger.debug("MatchService: Fetching matches for user. UserId: {}", userId);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("MatchService: User not found. UserId: {}", userId);
            return new IllegalArgumentException("User not found");
        });

        List<Match> matches = matchRepository.findByUser(user);
        return matches.stream()
                    .map(match -> {
                        Profile profile = match.getProfile();
                        MatchResponse matchResponse = new MatchResponse();
                        matchResponse.setId(profile.getId());
                        matchResponse.setNickname(profile.getNickname());
                        matchResponse.setSummary(profile.getSummary());
                        matchResponse.setFirstName(profile.getFirstName());
                        matchResponse.setSurname(profile.getSurname());
                        matchResponse.setEmail(profile.getEmail());
                        return matchResponse;
                    })
                    .collect(Collectors.toList());
    }
}
