package com.example.peasinapod.Repository;

import com.example.peasinapod.Data.Common.Like;
import com.example.peasinapod.Data.Common.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.peasinapod.Data.Common.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUser(User user);
    Optional<Like> findByUserIdAndProfileId(Long userId, Long profileId);
    @Query(value = "SELECT * FROM likes WHERE profile_id = :profileId", nativeQuery = true)
    List<Like> findByProfile(@Param("profileId") Long profileId);
}
