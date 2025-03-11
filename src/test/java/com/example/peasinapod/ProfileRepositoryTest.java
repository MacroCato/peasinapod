package com.example.peasinapod;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void testFindByExistingId() {
        Long profileId = 1L;
        Optional<Profile> profile = profileRepository.findById(profileId);
        assertTrue(profile.isPresent(), "Profile should be found");
    }

    @Test
    public void testFindByNonExistingId() {
        Long profileId = 1000L;
        Optional<Profile> profile = profileRepository.findById(profileId);
        assertFalse(profile.isPresent(), "Profile should not be found");
    }

    // @Test
    // public void testFindByEmail() {
    //     String email = "john.doe@example.com";
    //     Optional<Profile> profile = profileRepository.findByEmail(email);
    //     assertTrue(profile.isPresent(), "Profile should be found");
    // }
}