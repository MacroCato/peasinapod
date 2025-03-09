package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.DTO.MatchResponse;
import org.springframework.stereotype.Component;


@Component
public class MatchAdapterImpl implements GenericDTOAdapter<Profile, MatchResponse> {

    @Override
    public MatchResponse convertToDTO(Profile profile) {
        MatchResponse matchResponse = new MatchResponse();
        matchResponse.setId(profile.getId());
        matchResponse.setNickname(profile.getNickname());
        matchResponse.setSummary(profile.getSummary());
        matchResponse.setFirstName(profile.getFirstName());
        matchResponse.setSurname(profile.getSurname());
        matchResponse.setEmail(profile.getEmail());
        return matchResponse;
    }
}