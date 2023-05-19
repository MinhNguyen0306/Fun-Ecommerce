package com.example.funE.Dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class GroupDto {
    private int id;
    private String title;
    private String avatar;
}
