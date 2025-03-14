package com.example.peasinapod.Data.Common;

import lombok.*;
import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
