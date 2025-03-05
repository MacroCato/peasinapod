package com.example.peasinapod.Repository;

import com.example.peasinapod.Common.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.peasinapod.Common.User;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUser(User user);
}
