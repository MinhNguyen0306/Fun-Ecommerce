package com.example.funE.Services;

import com.example.funE.Dtos.CommentDto;
import com.example.funE.Entities.User;
import com.example.funE.Utils.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentDto getCommentById(Integer commentId);
    CommentDto createComment(String message, Integer groupPostId, Integer userId);
    CommentDto createChildComment(String message, Integer groupPostId, Integer userId, Integer parentId);
    void deleteComment(Integer commentId);
    CommentDto editComment(CommentDto commentDto, Integer commentId);
    List<CommentDto> getAllCommentOfGroupPost(Integer groupPostId);
    List<CommentDto> getAllChildComment(Integer commentId);
}
