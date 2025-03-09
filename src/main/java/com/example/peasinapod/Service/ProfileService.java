package com.example.peasinapod.Service;

import com.example.peasinapod.Data.Common.Profile;
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

// This is the service class (business logic) for the Profile entity
// It contains methods for saving, retrieving and deleting Profile objects
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileAdapter profileAdapter;

    @Autowired
    private GenericDTOAdapter<Profile, ProfileUserDTO> profileUserAdapter;

    // private CustomProfileRepository mockProfileRepository = new MockProfileRepository();

    public Profile saveProfile(Profile profile) {
        // Business logic: Ensure the email is unique
        if (profileRepository.findByEmail(profile.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
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
    public Profile updateProfile(Long id, Profile updatedProfile) {
        Optional<Profile> existingProfileOpt = profileRepository.findById(id);
        if (existingProfileOpt.isPresent()) {
            Profile existingProfile = existingProfileOpt.get();
            existingProfile.setFirstName(updatedProfile.getFirstName());
            existingProfile.setSurname(updatedProfile.getSurname());
            existingProfile.setEmail(updatedProfile.getEmail());
            existingProfile.setNickname(updatedProfile.getNickname());
            existingProfile.setSummary(updatedProfile.getSummary());
            return profileRepository.save(existingProfile);
        } else {
            throw new IllegalArgumentException("Profile not found");
        }
    }

    // Delete a profile by ID
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

    public List<ProfileDTO> getAllProfilesExceptUser(Long userId) {
        List<Profile> profiles = profileRepository.findAllProfilesExcept(userId);
        return profiles.stream()
                    .map(profileAdapter::convertToDTO)
                    .collect(Collectors.toList());
    }
}
