package com.example.funE.Services;

import com.example.funE.Dtos.*;
import com.example.funE.Entities.User;
import com.example.funE.Utils.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GroupService {
    GroupDto getGroupById(Integer id);
    GroupDto createGroup(String title, String avatar, Integer userId, Set<User> users);
    List<GroupDto> getAllGroup();
    Set<GroupDto> getAllGroupOfUser(Integer userId);
    String leaveGroup(Integer groupId, Integer userId);
    GroupDto joinGroup(Integer groupId, Integer userId);
    List<VideoDto> getAllVideoOfGroup(Integer groupId);
    List<PhotoDto> getAllPhotoOfGroup(Integer groupId);
    List<AudioDto> getAllAudioOfGroup(Integer groupId);
}
