package com.example.peasinapod.Data.Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LikeTest {
    private Like like;

    private User user;

    private Profile profile;

    @BeforeEach
    public void setUp() {
        like = new Like();
        user = new User();
        profile = new Profile();

        user.setId(1L);
        user.setEmail("password");

        profile.setId(1L);
        profile.setFirstName("John");
        profile.setSurname("Doe");
        profile.setNickname("Johnny");
        profile.setSummary("A brief summary");

        like.setId(1L);
        like.setUser(user);
        like.setProfile(profile);
    }

    @Test
    public void testGetId() {
        assertEquals(1L, like.getId());
    }

    @Test
    public void testSetId() {
        like.setId(2L);
        assertEquals(2L, like.getId());
    }

    @Test
    public void testGetUser() {
        assertEquals(user, like.getUser());
    }

    @Test
    public void testSetUser() {
        User newUser = new User();
        newUser.setId(2L);
        newUser.setEmail("password123");
        like.setUser(newUser);
        assertEquals(newUser, like.getUser());
    }

    @Test
    public void testGetProfile() {
        assertEquals(profile, like.getProfile());
    }

    @Test
    public void testSetProfile() {
        Profile newProfile = new Profile();
        newProfile.setId(2L);
        newProfile.setFirstName("Jane");
        newProfile.setSurname("Doe");
        newProfile.setNickname("Janey");
        newProfile.setSummary("A brief summary");
        like.setProfile(newProfile);
        assertEquals(newProfile, like.getProfile());
    }
}
