package com.example.peasinapod.Service;

import com.example.peasinapod.Common.Profile;
import com.example.peasinapod.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is the service class (business logic) for the Profile entity
// It contains methods for saving, retrieving and deleting Profile objects
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile saveProfile(Profile profile) {
        // Business logic: Ensure the email is unique
        if (profileRepository.findByEmail(profile.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return profileRepository.save(profile);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    // Update an existing profile
    public Profile updateProfile(Long id, Profile updatedProfile) {
        Optional<Profile> existingProfileOpt = profileRepository.findById(id);
        if (existingProfileOpt.isPresent()) {
            Profile existingProfile = existingProfileOpt.get();
            existingProfile.setFirstName(updatedProfile.getFirstName());
            existingProfile.setSurname(updatedProfile.getSurname());
            existingProfile.setEmail(updatedProfile.getEmail());
            return profileRepository.save(existingProfile);
        } else {
            throw new IllegalArgumentException("Profile not found");
        }
    }

    // Delete a profile by ID
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
