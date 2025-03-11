package com.example.peasinapod.Repository;

import com.example.peasinapod.Data.Common.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.peasinapod.Data.Common.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.user1 = :user OR m.user2 = :user")
    List<Match> findByUser(@Param("user") User user);
    @Query("SELECT m FROM Match m WHERE (m.user1 = :user1 AND m.user2 = :user2) OR (m.user1 = :user2 AND m.user2 = :user1)")
    Optional<Match> findByUser1AndUser2(@Param("user1") User user1, @Param("user2") User user2);
    Optional<Match> findByUser1OrUser2(@Param("user1") User user1, @Param("user2") User user2);
}
