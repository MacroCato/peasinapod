package com.example.peasinapod.Repository;

import com.example.peasinapod.Data.Common.Profile;
import java.util.List;
import java.util.Optional;

public interface CustomProfileRepository {
    List<Profile> getAll();
    Profile getById(Long profileId);
    //Optional<Profile> findByEmail(String email);
}