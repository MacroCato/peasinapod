package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileAdapterImpl implements ProfileAdapter {

    @Override
    public ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setNickname(profile.getNickname());
        profileDTO.setSummary(profile.getSummary());
        return profileDTO;
    }
}