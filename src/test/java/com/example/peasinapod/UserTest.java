package com.example.peasinapod;

import com.example.peasinapod.Common.Role;
import com.example.peasinapod.Common.User; 


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        user = new User();
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
    }

    @Test
    public void testGettersAndSetters() {
        user.setId(1L);
        user.setName("Joe Doe");
        user.setUsername("joedoe");
        user.setEmail("joedoe@example.com");
        user.setPassword("password");

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        assertEquals(1L, user.getId());
        assertEquals("Joe Doe", user.getName());
        assertEquals("joedoe", user.getUsername());
        assertEquals("joedoe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertEquals("ROLE_USER", user.getRoles().iterator().next().getName());
    }

    @Test
    public void testUserRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertEquals("ROLE_USER", user.getRoles().iterator().next().getName());
    }
}
