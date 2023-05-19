package com.example.funE.Dtos;

import com.example.funE.Entities.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class PhotoDto {
    private int id;
    private String photoName;
    private String photoUrl;
    private GroupPostDto groupPost;
}
