package com.example.funE.Dtos;

import com.example.funE.Entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter @Setter
public class CommentDto {
    private int id;
    private String message;
    private LocalDateTime createAt;
    private UserDto user;
}
