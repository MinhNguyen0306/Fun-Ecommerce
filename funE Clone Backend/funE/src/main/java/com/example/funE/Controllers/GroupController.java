package com.example.funE.Controllers;

import com.example.funE.Dtos.AudioDto;
import com.example.funE.Dtos.GroupDto;
import com.example.funE.Dtos.PhotoDto;
import com.example.funE.Dtos.VideoDto;
import com.example.funE.Entities.User;
import com.example.funE.Services.FileService;
import com.example.funE.Services.GroupService;
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
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @GetMapping("")
    public ResponseEntity<List<GroupDto>> getAllGroup() {
        List<GroupDto> groupDtoList = this.groupService.getAllGroup();
        return Optional.ofNullable(groupDtoList).isPresent() ?
                ResponseEntity.ok().body(groupDtoList) : ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<GroupDto>> getAllGroupOfUser(@PathVariable Integer userId) {
        Set<GroupDto> groupDtoSet = this.groupService.getAllGroupOfUser(userId);
        if(groupDtoSet == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(groupDtoSet);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<GroupDto> createGroup(
            @PathVariable Integer userId,
            @RequestParam("title") String title,
            @RequestParam("avatar") MultipartFile avatar,
            @RequestParam(value = "users") Set<User> users) throws IOException {
        String fileName = fileService.uploadImage(path, avatar);
        GroupDto createdGroup = this.groupService.createGroup(title, fileName, userId, users);
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Integer groupId) {
        GroupDto groupDto = this.groupService.getGroupById(groupId);
        return ResponseEntity.ok().body(groupDto);
    }

    @PatchMapping("/{groupId}/user/{userId}")
    public ResponseEntity<GroupDto> joinGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        GroupDto groupDto = this.groupService.joinGroup(groupId, userId);
        return Optional.ofNullable(groupDto).isPresent() ?
                new ResponseEntity<>(groupDto, HttpStatus.OK) : ResponseEntity.noContent().build();
    }

    @PutMapping("{groupId}/user/{userId}")
    public ResponseEntity<String> leaveGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        return this.groupService.leaveGroup(groupId,userId).equalsIgnoreCase("success") ?
                new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping("/{groupId}/video")
    public ResponseEntity<List<VideoDto>> getAllVideoOfGroup(@PathVariable Integer groupId) {
        List<VideoDto> videoDtoList = this.groupService.getAllVideoOfGroup(groupId);
        return ResponseEntity.ok(videoDtoList);
    }

    @GetMapping("/{groupId}/audio")
    public ResponseEntity<List<AudioDto>> getAllAudioOfGroup(@PathVariable Integer groupId) {
        List<AudioDto> audioDtoList = this.groupService.getAllAudioOfGroup(groupId);
        return ResponseEntity.ok(audioDtoList);
    }

    @GetMapping("/{groupId}/photo")
    public ResponseEntity<List<PhotoDto>> getAllPhotoOfGroup(@PathVariable Integer groupId) {
        List<PhotoDto> photoDtoList = this.groupService.getAllPhotoOfGroup(groupId);
        return ResponseEntity.ok(photoDtoList);
    }

}
