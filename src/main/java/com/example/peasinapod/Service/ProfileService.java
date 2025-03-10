package com.example.peasinapod.Service;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Repository.UserRepository;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import com.example.peasinapod.Data.Adapter.ProfileAdapter;
import com.example.peasinapod.Data.Adapter.GenericDTOAdapter;
import com.example.peasinapod.Data.DTO.ProfileUserDTO;
import com.example.peasinapod.Repository.ProfileRepository;
import com.example.peasinapod.Security.JwtTokenUtil;
//import com.example.peasinapod.Repository.CustomProfileRepository;
//import com.example.peasinapod.Mock.MockProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This is the service class (business logic) for the Profile entity
// It contains methods for saving, retrieving and deleting Profile objects
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileAdapter profileAdapter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenericDTOAdapter<Profile, ProfileUserDTO> profileUserAdapter;

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    // private CustomProfileRepository mockProfileRepository = new MockProfileRepository();

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<ProfileDTO> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                    .map(profileAdapter::convertToDTO)
                    .collect(Collectors.toList());
    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    public ProfileDTO getProfileDTOById(Long id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile != null) {
            return profileAdapter.convertToDTO(profile);
        } else {
            return null;
        }
    }

    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId).orElse(null);
    }

    public ProfileDTO getProfileDTOByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile != null) {
            return profileAdapter.convertToDTO(profile);
        } else {
            return null;
        }
    }

    public ProfileUserDTO getProfileUserDTOByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile != null) {
            return profileUserAdapter.convertToDTO(profile);
        } else {
            return null;
        }
    }

    // Update an existing profile
    public ProfileUserDTO updateProfile(Long id, ProfileUserDTO updatedProfile) {
        logger.debug("ProfileService: Updating profile with ID: {}", updatedProfile.getId());
        Optional<Profile> existingProfileOpt = profileRepository.findById(updatedProfile.getId());
        logger.debug("ProfileService: Existing profile: {}", existingProfileOpt);
        if (existingProfileOpt.isPresent()) {
            logger.debug("ProfileService: Profile found. Updating profile");
            Profile existingProfile = existingProfileOpt.get();
            existingProfile.setFirstName(updatedProfile.getFirstName());
            existingProfile.setSurname(updatedProfile.getSurname());
            existingProfile.setNickname(updatedProfile.getNickname());
            existingProfile.setSummary(updatedProfile.getSummary());
            // Profile savedProfile = profileRepository.save(existingProfile);
            // ProfileUserDTO profileUserDTO = profileUserAdapter.convertToDTO(savedProfile);
            // return profileUserDTO;
            return profileUserAdapter.convertToDTO(profileRepository.save(existingProfile));
        } else {
            throw new IllegalArgumentException("Profile not found");
        }
    }

    // Delete a profile by ID
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    public List<ProfileDTO> getAllProfilesExceptUser(Long userId) {
        logger.debug("ProfileService: Fetching all profiles except for userId: {}", userId);
        
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("ProfileService: User not found. UserId: {}", userId);
            return new IllegalArgumentException("User not found");
        });
        List<Profile> profiles = profileRepository.findAllProfilesExcept(user);
        return profiles.stream()
                    .map(profileAdapter::convertToDTO)
                    .collect(Collectors.toList());
    }
}
