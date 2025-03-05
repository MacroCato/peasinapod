package com.example.peasinapod.Repository;

import com.example.peasinapod.Common.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

// This is the repository interface for the Profile entity
// It extends the JpaRepository interface, which provides methods for performing
// CRUD (Create, Read, Update and Delete) operations on the entity
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);

    @Query("SELECT p FROM Profile p WHERE p.id <> :userId")
    List<Profile> findAllProfilesExcept(@Param("userId") Long userId);
      
    //List<Profile> findAllByUserNot(Long userId);
}
