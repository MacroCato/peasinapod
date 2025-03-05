package com.example.peasinapod.Common;

import com.example.peasinapod.Common.Profile;
import com.example.peasinapod.Common.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rejects")
@Data
public class Reject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
}