package com.example.peasinapod.Repository;

import com.example.peasinapod.Data.Common.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.peasinapod.Data.Common.User;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByUser(User user);
}
