package com.example.funE.Utils;

import com.example.funE.Dtos.GroupPostDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter @Setter
public class GroupPostResponse {
    List<GroupPostDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
