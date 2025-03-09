package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.DTO.ProfileUserDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileUserAdapterImpl implements GenericDTOAdapter<Profile, ProfileUserDTO> {

    @Override
    public ProfileUserDTO convertToDTO(Profile profile) {
        ProfileUserDTO profileUserDTO = new ProfileUserDTO();
        profileUserDTO.setId(profile.getId());
        profileUserDTO.setFirstName(profile.getFirstName());
        profileUserDTO.setSurname(profile.getSurname());
        profileUserDTO.setEmail(profile.getEmail());
        profileUserDTO.setNickname(profile.getNickname());
        profileUserDTO.setSummary(profile.getSummary());
        return profileUserDTO;
    }
}