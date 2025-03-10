package com.example.peasinapod.Data.DTO;

import lombok.*;
import com.example.peasinapod.Data.Common.Profile;
import java.util.List;

@Data
public class ProfileDTO {
    private Long id;
    private String nickname;
    private String summary;
}
