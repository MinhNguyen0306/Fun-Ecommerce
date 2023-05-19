package com.example.funE.Dtos;

import com.example.funE.Entities.GroupPost;
import com.example.funE.Entities.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class VideoDto {
    private int id;
    private String videoName;
    private String videoUrl;
    private GroupPostDto groupPost;
}
