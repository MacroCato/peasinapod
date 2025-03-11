package com.example.peasinapod.Controller;

import java.util.List;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import com.example.peasinapod.Data.DTO.ProfileUserDTO;
import com.example.peasinapod.Service.ProfileService;
import com.example.peasinapod.Security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This is the controller class for the Profile entity. This 
// is the entry point for the clients via the REST API.
// It handles HTTP requests from clients and sends responses
// It uses the ProfileService class to perform business logic
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    // Inject the ProfileService class
    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    // Create a new user profile
    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        try {
            Profile savedProfile = profileService.saveProfile(profile);
            return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get a list of all user profiles
    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        List<ProfileDTO> profiles = profileService.getAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileUserDTO> getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer " prefix
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        ProfileUserDTO profileUserDTO = profileService.getProfileUserDTOByUserId(userId);
        return new ResponseEntity<>(profileUserDTO, HttpStatus.OK);
    }
    
    // Get a specific user profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileDTOById(@PathVariable Long id) {
        ProfileDTO profileDTO = profileService.getProfileDTOById(id);
        if (profileDTO != null) {
            return new ResponseEntity<>(profileDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get a specific user profile by ID
    @GetMapping("/user/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        Profile profile = profileService.getProfileById(id);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get a list of all user profiles except the specified user
    @GetMapping("/except/{userId}")
    public ResponseEntity<List<ProfileDTO>> getAllProfilesExceptUser(@PathVariable Long userId) {
        try {
            logger.debug("ProfileController: Getting all profiles except user's with ID: {}", userId);
            List<ProfileDTO> profilesDTO = profileService.getAllProfilesExceptUser(userId);
            logger.debug("ProfileController: Profiles retrieved");
            return new ResponseEntity<>(profilesDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("ProfileController: User not found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing user profile
    @PutMapping("/{id}")
    public ResponseEntity<ProfileUserDTO> updateProfile(@PathVariable Long id, @RequestBody ProfileUserDTO updatedProfile) {
        try {
            logger.debug("ProfileController: Updating profile with ID: {}", id);
            ProfileUserDTO profileUserDTO = profileService.updateProfile(id, updatedProfile);
            logger.debug("ProfileController: Profile updated");
            return new ResponseEntity<>(profileUserDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user profile by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        try {
            profileService.deleteProfile(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}