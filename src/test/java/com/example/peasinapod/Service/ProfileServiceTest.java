package com.example.peasinapod.Service;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import com.example.peasinapod.Data.Common.User;
import com.example.peasinapod.Repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.easymock.EasyMock.*;

public class ProfileServiceTest {

    private ProfileRepository profileRepository;

    private ProfileService profileService;

    private User user1;
    private User user2;
    private User user3;
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;
    private ProfileDTO profileDTO1;
    private ProfileDTO profileDTO2;
    private ProfileDTO profileDTO3;

    @BeforeEach
    public void setUp() {
        profileRepository = EasyMock.createMock(ProfileRepository.class);
        profileService = new ProfileService();
        profileService.setProfileRepository(profileRepository);

        user1 = new User();
        user1.setId(1L);
        user1.setEmail("johndoe@example.com");
        user1.setPassword("password");

        user2 = new User();
        user2.setId(2L);
        user2.setEmail("janedoe@example.com");
        user2.setPassword("password");

        user3 = new User();
        user3.setId(3L);
        user3.setEmail("joebloggs@example.com");
        user3.setPassword("password");

        profile1 = new Profile();
        profile1.setId(1L);
        profile1.setFirstName("John");
        profile1.setSurname("Doe");
        profile1.setNickname("Johnny");
        profile1.setSummary("A brief summary");
        profile1.setUser(user1);

        profile2 = new Profile();
        profile2.setId(2L);
        profile2.setFirstName("Jane");
        profile2.setSurname("Doe");
        profile2.setNickname("Janey");
        profile2.setSummary("A brief summary");
        profile2.setUser(user2);

        profile3 = new Profile();
        profile3.setId(3L);
        profile3.setFirstName("Joe");
        profile3.setSurname("Bloggs");
        profile3.setNickname("Joey");
        profile3.setSummary("A brief summary");
        profile3.setUser(user3);

        profileDTO1 = new ProfileDTO();
        profileDTO1.setId(1L);
        profileDTO1.setNickname("Johnny");
        profileDTO1.setSummary("A brief summary");

        profileDTO2 = new ProfileDTO();
        profileDTO2.setId(2L);
        profileDTO2.setNickname("Janey");
        profileDTO2.setSummary("A brief summary");

        profileDTO3 = new ProfileDTO();
        profileDTO3.setId(3L);
        profileDTO3.setNickname("Joey");
        profileDTO3.setSummary("A brief summary");
    }

    @Test
    public void testSaveProfile() {

        expect(profileRepository.save(profile1)).andReturn(profile1);
        replay(profileRepository);

        Profile result = profileService.saveProfile(profile1);
        assertEquals(profile1.getId(), result.getId());
        assertEquals(profile1.getFirstName(), result.getFirstName());
        assertEquals(profile1.getSurname(), result.getSurname());
        assertEquals(profile1.getNickname(), result.getNickname());
        assertEquals(profile1.getSummary(), result.getSummary());
        assertEquals(profile1.getUser(), result.getUser());

        verify(profileRepository);
    }

    @Test
    public void testGetProfileById() {
        expect(profileRepository.findById(1L)).andReturn(Optional.of(profile1));
        replay(profileRepository);

        Profile result = profileService.getProfileById(1L);
        assertEquals(profile1.getId(), result.getId());
        assertEquals(profile1.getFirstName(), result.getFirstName());
        assertEquals(profile1.getSurname(), result.getSurname());
        assertEquals(profile1.getNickname(), result.getNickname());
        assertEquals(profile1.getSummary(), result.getSummary());
        assertEquals(profile1.getUser(), result.getUser());
        assertEquals(profile1, result);
    }
}