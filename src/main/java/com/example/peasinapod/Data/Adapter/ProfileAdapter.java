package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.DTO.ProfileDTO;

public interface ProfileAdapter {
    ProfileDTO convertToDTO(Profile profile);
}