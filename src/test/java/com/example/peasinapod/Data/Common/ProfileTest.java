package com.example.peasinapod.Data.Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileTest {

    private Profile profile;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("password");

        profile = new Profile();
        profile.setId(1L);
        profile.setFirstName("Joe");
        profile.setSurname("Bloggs");
        profile.setNickname("Joey");
        profile.setSummary("A brief summary");
        profile.setUser(user);
    }

    @Test
    public void testGetId() {
        assertEquals(1L, profile.getId());
    }

    @Test
    public void testSetId() {
        profile.setId(2L);
        assertEquals(2L, profile.getId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Joe", profile.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        profile.setFirstName("John");
        assertEquals("John", profile.getFirstName());
    }

    @Test
    public void testGetSurname() {
        assertEquals("Bloggs", profile.getSurname());
    }

    @Test
    public void testSetSurname() {
        profile.setSurname("Smith");
        assertEquals("Smith", profile.getSurname());
    }

    @Test
    public void testGetNickname() {
        assertEquals("Joey", profile.getNickname());
    }

    @Test
    public void testSetNickname() {
        profile.setNickname("Johnny");
        assertEquals("Johnny", profile.getNickname());
    }

    @Test
    public void testGetSummary() {
        assertEquals("A brief summary", profile.getSummary());
    }

    @Test
    public void testSetSummary() {
        profile.setSummary("A longer summary");
        assertEquals("A longer summary", profile.getSummary());
    }

    @Test
    public void testGetUser() {
        assertEquals(user, profile.getUser());
    }

    @Test
    public void testSetUser() {
        User newUser = new User();
        newUser.setId(2L);
        newUser.setEmail("password123");
        profile.setUser(newUser);
        assertEquals(newUser, profile.getUser());
    }
    
}
