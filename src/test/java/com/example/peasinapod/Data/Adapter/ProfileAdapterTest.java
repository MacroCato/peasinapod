package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileAdapterTest {

    private ProfileAdapter profileAdapter;
    private Profile profile;
    private ProfileDTO profileDTO;

    @BeforeEach
    public void setUp() {
        profileAdapter = new ProfileAdapterImpl();

        profile = new Profile();
        profile.setId(1L);
        profile.setFirstName("John");
        profile.setSurname("Doe");
        profile.setNickname("Johnny");
        profile.setSummary("A brief summary");

        profileDTO = new ProfileDTO();
        profileDTO.setId(1L);
        profileDTO.setNickname("Johnny");
        profileDTO.setSummary("A brief summary");
    }

    @Test
    public void testConvertToDTO() {
        ProfileDTO result = profileAdapter.convertToDTO(profile);
        assertEquals(profileDTO.getId(), result.getId());
        assertEquals(profileDTO.getNickname(), result.getNickname());
        assertEquals(profileDTO.getSummary(), result.getSummary());
    }
}