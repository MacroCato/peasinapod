package com.example.peasinapod.DTO;

import lombok.*;
import com.example.peasinapod.DTO.ProfileDTO;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MatchResponse extends ProfileDTO {
    private String firstName;
    private String surname;
    private String email;
}
