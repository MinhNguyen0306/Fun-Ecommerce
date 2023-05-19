package com.example.funE.Dtos;

import com.example.funE.Entities.GroupPost;
import com.example.funE.Entities.User;
import lombok.Data;

@Data
public class LikePostDto {
    private int id;

    private User user;
    private GroupPost groupPost;
}
