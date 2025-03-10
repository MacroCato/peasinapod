package com.example.peasinapod.Data.Common;

import com.example.peasinapod.Data.Common.Profile;
import com.example.peasinapod.Data.Common.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matches")
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_1_id", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_2_id", referencedColumnName = "id")
    private User user2;
}
