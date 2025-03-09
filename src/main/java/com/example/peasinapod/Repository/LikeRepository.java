package com.example.peasinapod.Repository;

import com.example.peasinapod.Data.Common.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.peasinapod.Data.Common.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUser(User user);
    Optional<Like> findByUserIdAndProfileId(Long userId, Long profileId);
}
