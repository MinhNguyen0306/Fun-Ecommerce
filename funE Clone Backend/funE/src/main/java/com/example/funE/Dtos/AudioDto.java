package com.example.funE.Dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class AudioDto {
    private int id;
    private String audioName;
    private String audioUrl;
    private GroupPostDto groupPost;
}
