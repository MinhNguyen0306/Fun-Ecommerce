package com.example.funE.Services.Impl;

import com.example.funE.Dtos.AudioDto;
import com.example.funE.Dtos.GroupDto;
import com.example.funE.Dtos.PhotoDto;
import com.example.funE.Dtos.VideoDto;
import com.example.funE.Entities.*;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.*;
import com.example.funE.Services.GroupService;
import com.example.funE.Utils.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private PhotoRepo photoRepo;
    @Autowired
    private AudioRepo audioRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GroupDto getGroupById(Integer id) {
        Group group = this.groupRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", id));
        return groupToDto(group);
    }

    @Override
    public GroupDto createGroup(String title, String avatar, Integer userId, Set<User> users) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Group group = new Group();
        group.setTitle(title);
        group.setAvatar(avatar);
        group.addUser(user);
        group.setGroupFollowers(users);
        Group createdGroup = this.groupRepo.save(group);
        return groupToDto(createdGroup);
    }

    @Override
    public List<GroupDto> getAllGroup() {
        List<Group> groups = this.groupRepo.findAll();
        List<GroupDto> groupDtoList = groups.stream().map(group -> groupToDto(group)).collect(Collectors.toList());
        return groupDtoList;
    }

    @Override
    public Set<GroupDto> getAllGroupOfUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Set<Group> groupsOfUser = Optional.ofNullable(user.getGroups()).isPresent() ?
                Optional.of(user.getGroups()).get() : null;
        if(groupsOfUser != null) {
            return groupsOfUser.stream().map(group -> this.groupToDto(group)).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public String leaveGroup(Integer groupId, Integer userId) {
        Group group = this.groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Set<User> userSet = group.getGroupFollowers();
        boolean checkMatchUser = userSet.stream().anyMatch(u -> u.getId() == userId);
        if(checkMatchUser) {
            user.removeGroup(group);
            group.removeUser(user);
            this.userRepo.save(user);
            this.groupRepo.save(group);
            return "success";
        }
        return "failed";
    }

    @Override
    public GroupDto joinGroup(Integer groupId, Integer userId) {
        Group group = this.groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "ID", groupId));
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.getGroups().add(group);
        boolean checkUserInGroup = group.getGroupFollowers().stream().anyMatch(follower -> follower.getId() == groupId);
        if(checkUserInGroup) {
            this.userRepo.save(user);
            return groupToDto(group);
        }
        return null;
    }

    @Override
    public List<PhotoDto> getAllPhotoOfGroup(Integer groupId) {
        List<Photo> photos = this.photoRepo.findAll();
        List<Photo> photoFilter = photos.stream()
                .filter(photo -> photo.getGroupPost() != null && photo.getGroupPost().getGroup().getId() == groupId).collect(Collectors.toList());
        List<PhotoDto> photoDtoList = photoFilter.stream()
                .map(photo -> this.modelMapper.map(photo, PhotoDto.class)).collect(Collectors.toList());
        return photoDtoList;
    }

    @Override
    public List<AudioDto> getAllAudioOfGroup(Integer groupId) {
        List<Audio> audios = this.audioRepo.findAll();
        List<Audio> audioFilter = audios.stream()
                .filter(audio -> audio.getGroupPost() != null && audio.getGroupPost().getGroup().getId() == groupId).collect(Collectors.toList());
        List<AudioDto> audioDtoList = audioFilter.stream()
                .map(audio -> this.modelMapper.map(audio, AudioDto.class)).collect(Collectors.toList());
        return audioDtoList;
    }

    @Override
    public List<VideoDto> getAllVideoOfGroup(Integer groupId) {
        List<Video> videos = this.videoRepo.findAll();
        List<Video> videoFilter = videos.stream()
                .filter(video -> video.getGroupPost() != null && video.getGroupPost().getGroup().getId() == groupId).collect(Collectors.toList());
        List<VideoDto> videoDtoList = videoFilter.stream()
                .map(video -> this.modelMapper.map(video, VideoDto.class)).collect(Collectors.toList());
        return videoDtoList;
    }


    private Group dtoToGroup(GroupDto groupDto) {
        return this.modelMapper.map(groupDto, Group.class);
    }

    private GroupDto groupToDto(Group group) {
        return this.modelMapper.map(group, GroupDto.class);
    }
}
