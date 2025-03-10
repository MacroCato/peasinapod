package com.example.peasinapod.Data.DTO;

import lombok.*;
import com.example.peasinapod.Data.DTO.ProfileDTO;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileUserDTO extends ProfileDTO {
    private String firstName;
    private String surname;
    private Long userId;
}