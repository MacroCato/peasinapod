package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Data.DTO.ProfileUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileUserAdapterTest {

    private GenericDTOAdapter<Profile, ProfileUserDTO> profileUserAdapter;
    private Profile profile;
    private ProfileUserDTO profileUserDTO;
    private User user;

    @BeforeEach
    public void setUp() {
        profileUserAdapter = new ProfileUserAdapterImpl();

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");

        profile = new Profile();
        profile.setId(1L);
        profile.setFirstName("John");
        profile.setSurname("Doe");
        profile.setNickname("Johnny");
        profile.setSummary("A brief summary");
        profile.setUser(user);

        profileUserDTO = new ProfileUserDTO();
        profileUserDTO.setId(1L);
        profileUserDTO.setFirstName("John");
        profileUserDTO.setSurname("Doe");
        profileUserDTO.setNickname("Johnny");
        profileUserDTO.setSummary("A brief summary");
        profileUserDTO.setUserId(1L);
    }

    @Test
    public void testConvertToDTO() {
        ProfileUserDTO result = profileUserAdapter.convertToDTO(profile);
        assertEquals(profileUserDTO.getId(), result.getId());
        assertEquals(profileUserDTO.getFirstName(), result.getFirstName());
        assertEquals(profileUserDTO.getSurname(), result.getSurname());
        assertEquals(profileUserDTO.getNickname(), result.getNickname());
        assertEquals(profileUserDTO.getSummary(), result.getSummary());
        assertEquals(profileUserDTO.getUserId(), result.getUserId());
    }
}