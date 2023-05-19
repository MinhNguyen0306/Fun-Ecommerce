package com.example.funE.Services.Impl;

import com.example.funE.Dtos.CommentDto;
import com.example.funE.Entities.Comment;
import com.example.funE.Entities.GroupPost;
import com.example.funE.Entities.User;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.CommentRepo;
import com.example.funE.Repositories.GroupPostRepo;
import com.example.funE.Repositories.UserRepo;
import com.example.funE.Services.CommentService;
import com.example.funE.Utils.CommentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private GroupPostRepo groupPostRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto getCommentById(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        return commentToDto(comment);
    }

    @Override
    public CommentDto createComment(String message, Integer groupPostId, Integer userId) {
        GroupPost groupPost = this.groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPost", "Id", groupPostId));
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setUser(user);
        comment.setGroupPost(groupPost);
        comment.setCreateAt(LocalDateTime.now());

        Comment commentSaved = this.commentRepo.save(comment);
        return commentToDto(commentSaved);
    }

    @Override
    public CommentDto createChildComment(String message, Integer groupPostId, Integer userId, Integer parentId) {
        GroupPost groupPost = this.groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPost", "Id", groupPostId));
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Comment parentComment = commentRepo.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", parentId));
        Comment comment = new Comment();
        comment.setParentComment(parentComment);
        comment.setMessage(message);
        comment.setUser(user);
        comment.setGroupPost(groupPost);
        comment.setCreateAt(LocalDateTime.now());

        Comment commentSaved = this.commentRepo.save(comment);
        return commentToDto(commentSaved);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        this.commentRepo.delete(comment);
    }

    @Override
    public CommentDto editComment(CommentDto commentDto, Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        comment.setMessage(commentDto.getMessage());
        comment.setEditAt(LocalDateTime.now());
        return commentToDto(comment);
    }
    @Override
    public List<CommentDto> getAllCommentOfGroupPost(Integer groupPostId) {
        List<Comment> commentList = this.commentRepo.findAll();
        List<Comment> commentFilter = commentList.stream()
                .filter(comment -> comment.getGroupPost() != null &&
                        comment.getGroupPost().getId() == groupPostId &&
                        comment.getParentComment() == null)
                .collect(Collectors.toList());
        List<CommentDto> commentDtoList = commentFilter.stream()
                .map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public List<CommentDto> getAllChildComment(Integer commentId) {
        List<Comment> comments = commentRepo.findAll();
        List<Comment> childComments = comments.stream()
                .filter(comment -> comment.getParentComment() != null && comment.getParentComment().getId() == commentId).collect(Collectors.toList());
        List<CommentDto> commentDtoList = childComments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentDtoList;
    }

    private Comment dtoToComment(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }

    private CommentDto commentToDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }
}
