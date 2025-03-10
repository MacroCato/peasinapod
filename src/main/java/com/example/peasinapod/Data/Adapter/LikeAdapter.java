package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Like;
import com.example.peasinapod.Data.DTO.LikeDTO;

public interface LikeAdapter {
    LikeDTO convertToDTO(Like like);
}
