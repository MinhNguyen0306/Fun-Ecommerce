package com.example.funE.Dtos;

import com.example.funE.Entities.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter @Setter
public class GroupPostDto {
    private int id;
    private String message;
    private LocalDateTime datePost;
    private UserDto user;
    private GroupDto group;
    private List<CommentDto> comments;

//    private List<PhotoDto> photos;
//    private List<VideoDto> videos;
//    private List<AudioDto> audioSet;
}
