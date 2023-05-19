package com.example.funE.Controllers;

import com.example.funE.Dtos.CommentDto;
import com.example.funE.Services.CommentService;
import com.example.funE.Utils.ApiResponse;
import com.example.funE.Utils.AppConstants;
import com.example.funE.Utils.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer commentId) {
        CommentDto commentDto = this.commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ApiResponse("Delete comment success", true);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> editComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId) {
        CommentDto editedComment = this.commentService.editComment(commentDto, commentId);
        return ResponseEntity.ok(editedComment);
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> createComment(@RequestParam("message") String message,
                                                    @RequestParam("groupPostId") Integer groupPostId,
                                                    @RequestParam("userId") Integer userId) {
        CommentDto createdComment = this.commentService.createComment(message, groupPostId, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PostMapping("/child_comment")
    public ResponseEntity<CommentDto> createChildComment(
            @RequestParam("message") String message,
            @RequestParam("groupPostId") Integer groupPostId,
            @RequestParam("userId") Integer userId,
            @RequestParam("parentId") Integer parentId) {
        CommentDto createdComment = this.commentService.createChildComment(message, groupPostId, userId, parentId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<CommentDto>> getAllCommentOfGroupPost(@RequestParam("groupPostId") Integer groupPostId) {
        List<CommentDto> commentResponse = this.commentService.getAllCommentOfGroupPost(groupPostId);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/child_comment")
    public ResponseEntity<List<CommentDto>> getAllChildComment(@RequestParam("commentId") Integer commentId) {
        List<CommentDto> commentDtoList = commentService.getAllChildComment(commentId);
        return ResponseEntity.ok(commentDtoList);
    }
}
