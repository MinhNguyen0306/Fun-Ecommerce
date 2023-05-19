package com.example.funE.Services;

import com.example.funE.Dtos.*;
import com.example.funE.Entities.Video;
import com.example.funE.Utils.ApiResponse;
import com.example.funE.Utils.GroupPostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GroupPostService {
    GroupPostDto createGroupPost(String description, MultipartFile[] files, Integer groupId, Integer userId);
    void deleteGroupPost(Integer groupPostId);
    GroupPostDto getGroupPostById(Integer groupPostId);
    GroupPostResponse getAllGroupPost(Integer groupId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    Set<GroupPostDto> getAllGroupPostOfUser(Integer userId);
    List<VideoDto> getAllVideoOfGroupPost(Integer groupPostId);
    List<AudioDto> getAllAudioOfGroupPost(Integer groupPostId);
    List<PhotoDto> getAllPhotoOfGroupPost(Integer groupPostId);

    List<UserDto> getAllUserLikedOfPost(Integer groupPostId);
    Integer getNumberLikedOfPost(Integer groupPostId);
    String deleteLiked(Integer groupPostId, Integer userId);
    String addLiked(Integer groupPostId, Integer userId);
}
