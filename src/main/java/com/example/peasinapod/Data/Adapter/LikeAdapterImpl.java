package com.example.peasinapod.Data.Adapter;

import com.example.peasinapod.Data.Common.Like;
import com.example.peasinapod.Data.DTO.LikeDTO;
import org.springframework.stereotype.Component;

@Component
public class LikeAdapterImpl implements LikeAdapter {

    @Override
    public LikeDTO convertToDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setUserId(like.getUser().getId());
        likeDTO.setProfileId(like.getProfile().getId());
        return likeDTO;
    }
}