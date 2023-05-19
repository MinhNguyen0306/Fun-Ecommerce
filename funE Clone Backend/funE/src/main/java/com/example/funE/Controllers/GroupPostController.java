package com.example.funE.Controllers;

import com.example.funE.Dtos.AudioDto;
import com.example.funE.Dtos.GroupPostDto;
import com.example.funE.Dtos.PhotoDto;
import com.example.funE.Dtos.VideoDto;
import com.example.funE.Services.FileService;
import com.example.funE.Services.GroupPostService;
import com.example.funE.Utils.ApiResponse;
import com.example.funE.Utils.AppConstants;
import com.example.funE.Utils.GroupPostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/group_post")
public class GroupPostController {
    @Autowired
    private GroupPostService groupPostService;
    @Value("${project.image}")
    private String path;
    @Autowired
    private FileService fileService;

    @PostMapping("")
    public ResponseEntity<GroupPostDto> createGroupPost(@RequestParam("message") String message,
                                                       @RequestParam("groupId") Integer groupId,
                                                       @RequestParam("media") MultipartFile[] files,
                                                       @RequestParam("userId") Integer userId) {
        GroupPostDto createdGroupPost = this.groupPostService.createGroupPost(message,files,groupId,userId);
        return new ResponseEntity<>(createdGroupPost, HttpStatus.CREATED);
    }

    @DeleteMapping("")
    public ApiResponse deleteGroupPost(@RequestParam("groupPostId") Integer groupPostId) {
        this.groupPostService.deleteGroupPost(groupPostId);
        return new ApiResponse("Delete Post success", true);
    }

    @GetMapping("/{groupPostId}")
    public ResponseEntity<GroupPostDto> getGroupPostById(@PathVariable Integer groupPostId) {
        GroupPostDto groupPostDto = this.groupPostService.getGroupPostById(groupPostId);
        return new ResponseEntity<>(groupPostDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<GroupPostResponse> getAllGroupPost(
            @RequestParam("groupId") Integer groupId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        GroupPostResponse groupPostResponse = this.groupPostService.getAllGroupPost(groupId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(groupPostResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/media/{mediaName}", produces = {MediaType.IMAGE_JPEG_VALUE, "audio/*", "video/*"})
    public void getImageResource(@PathVariable String mediaName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, mediaName);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping(value = "/video/{videoName}", produces = "audio/mp4")
    public void getVideoResource(@PathVariable String videoName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, videoName);
        response.setContentType("audio/mp4");
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<GroupPostDto>> getAllGroupPostOfUser(@PathVariable Integer userId) {
        Set<GroupPostDto> groupPostDtoSet = this.groupPostService.getAllGroupPostOfUser(userId);
        return new ResponseEntity<>(groupPostDtoSet, HttpStatus.OK);
    }

    @GetMapping("/{groupPostId}/image")
    public ResponseEntity<List<PhotoDto>> getAllPhotoOfGroupPost(@PathVariable Integer groupPostId) {
        List<PhotoDto> photoDtoList = this.groupPostService.getAllPhotoOfGroupPost(groupPostId);
        return ResponseEntity.ok(photoDtoList);
    }

    @GetMapping("/{groupPostId}/video")
    public ResponseEntity<List<VideoDto>> getAllVideoOfGroupPost(@PathVariable Integer groupPostId) {
        List<VideoDto> videoDtoList = this.groupPostService.getAllVideoOfGroupPost(groupPostId);
        return ResponseEntity.ok(videoDtoList);
    }

    @GetMapping("/{groupPostId}/audio")
    public ResponseEntity<List<AudioDto>> getAllAudioOfGroupPost(@PathVariable Integer groupPostId) {
        List<AudioDto> audioDtoList = this.groupPostService.getAllAudioOfGroupPost(groupPostId);
        return ResponseEntity.ok(audioDtoList);
    }

}
