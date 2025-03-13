package com.example.peasinapod.Data.Common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testGetEmail() {
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }
}