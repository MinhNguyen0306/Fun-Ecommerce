package com.example.funE.Services.Impl;

import com.example.funE.Dtos.*;
import com.example.funE.Entities.*;
import com.example.funE.Exceptions.ResourceNotFoundException;
import com.example.funE.Repositories.*;
import com.example.funE.Services.FileService;
import com.example.funE.Services.GroupPostService;
import com.example.funE.Utils.ApiResponse;
import com.example.funE.Utils.FileUtils;
import com.example.funE.Utils.GroupPostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GroupPostServiceImpl implements GroupPostService {

    @Autowired
    private GroupPostRepo groupPostRepo;
    @Autowired
    private LikePostRepo likePostRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private PhotoRepo photoRepo;
    @Autowired
    private AudioRepo audioRepo;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GroupPostDto createGroupPost(String description, MultipartFile[] files, Integer groupId, Integer userId) {
        Group group = this.groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        GroupPost groupPost = new GroupPost();
        groupPost.setMessage(description);
        groupPost.setUser(user);
        groupPost.setGroup(group);
        groupPost.setDatePost(LocalDateTime.now());
        GroupPost groupPostSaved = this.groupPostRepo.save(groupPost);
        Arrays.stream(files).forEach(multipartFile -> {
            try {
                String fileName = fileService.uploadImage(path, multipartFile);
                Optional<String> extensionOptional = FileUtils.getExtensionByStringHandling(fileName);
                if(extensionOptional.isPresent()) {
                    String extension = extensionOptional.get();
                    switch (extension) {
                        case "mp4":
                            VideoDto videoDto = new VideoDto();
                            videoDto.setVideoName(multipartFile.getOriginalFilename()
                                    .substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                            videoDto.setVideoUrl(fileName);
                            Video videoSaved = dtoToVideo(videoDto);
                            videoSaved.setGroupPost(groupPostSaved);
                            videoRepo.save(videoSaved);
                            break;
                        case "mp3":
                            AudioDto audioDto = new AudioDto();
                            audioDto.setAudioName(multipartFile.getOriginalFilename()
                                    .substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                            audioDto.setAudioUrl(fileName);
                            Audio audioSaved = dtoToAudio(audioDto);
                            audioSaved.setGroupPost(groupPostSaved);
                            audioRepo.save(audioSaved);
                            break;
                        default:
                            PhotoDto photoDto = new PhotoDto();
                            photoDto.setPhotoName(multipartFile.getOriginalFilename()
                                    .substring(0, multipartFile.getOriginalFilename().lastIndexOf(".")));
                            photoDto.setPhotoUrl(fileName);
                            Photo photoSaved = dtoToPhoto(photoDto);
                            photoSaved.setGroupPost(groupPostSaved);
                            photoRepo.save(photoSaved);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return groupPostToDto(groupPostSaved);
    }

    @Override
    public void deleteGroupPost(Integer groupPostId) {
        GroupPost groupPost = this.groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPost", "Id", groupPostId));
        this.groupPostRepo.delete(groupPost);
    }

    @Override
    public GroupPostDto getGroupPostById(Integer groupPostId) {
        GroupPost groupPost = this.groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPost", "Id", groupPostId));
        return groupPostToDto(groupPost);
    }

    @Override
    public GroupPostResponse getAllGroupPost(Integer groupId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Group group = this.groupRepo.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "Id", groupId));
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<GroupPost> groupPostPage = this.groupPostRepo.findAll(pageable);

        List<GroupPost> groupPostList = groupPostPage.getContent()
                .stream().filter(groupPost -> groupPost.getGroup().getId() == groupId).collect(Collectors.toList());
        List<GroupPostDto> groupPostDtoList = groupPostList.stream().map(groupPost -> groupPostToDto(groupPost)).collect(Collectors.toList());

        GroupPostResponse groupPostResponse = new GroupPostResponse();
        groupPostResponse.setContent(groupPostDtoList);
        groupPostResponse.setPageNumber(pageable.getPageNumber());
        groupPostResponse.setPageSize(pageable.getPageSize());
        groupPostResponse.setTotalPages(groupPostPage.getTotalPages());
        groupPostResponse.setTotalElements(groupPostPage.getTotalElements());
        groupPostResponse.setLastPage(groupPostPage.isLast());

        return groupPostResponse;
    }

    @Override
    public Set<GroupPostDto> getAllGroupPostOfUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Set<GroupPost> groupPostSet = user.getGroupPosts();
        Set<GroupPostDto> groupPostDtoSet = groupPostSet.stream().map(groupPost -> groupPostToDto(groupPost)).collect(Collectors.toSet());
        return groupPostDtoSet;
    }

    @Override
    public List<VideoDto> getAllVideoOfGroupPost(Integer groupPostId) {
        List<Video> videos = this.videoRepo.findAll();
        List<Video> videoFilter = videos.stream()
                .filter(video -> video.getGroupPost() != null && video.getGroupPost().getId() == groupPostId).collect(Collectors.toList());
        List<VideoDto> videoDtoList = videoFilter.stream()
                .map(video -> this.modelMapper.map(video, VideoDto.class)).collect(Collectors.toList());
        return videoDtoList;
    }

    @Override
    public List<AudioDto> getAllAudioOfGroupPost(Integer groupPostId) {
        List<Audio> audioList = this.audioRepo.findAll();
        List<Audio> audioFilter = audioList.stream()
                .filter(audio -> audio.getGroupPost() != null && audio.getGroupPost().getId() == groupPostId).collect(Collectors.toList());
        List<AudioDto> audioDtoList = audioFilter.stream()
                .map(audio -> this.modelMapper.map(audio, AudioDto.class)).collect(Collectors.toList());
        return audioDtoList;
    }

    @Override
    public List<PhotoDto> getAllPhotoOfGroupPost(Integer groupPostId) {
        List<Photo> photos = this.photoRepo.findAll();
        List<Photo> photoFilter = photos.stream()
                .filter(photo -> photo.getGroupPost() != null && photo.getGroupPost().getId() == groupPostId).collect(Collectors.toList());
        List<PhotoDto> photoDtoList = photoFilter.stream()
                .map(photo -> this.modelMapper.map(photo, PhotoDto.class)).collect(Collectors.toList());
        return photoDtoList;
    }

    @Override
    public List<UserDto> getAllUserLikedOfPost(Integer groupPostId) {
        GroupPost groupPost = groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPost", "Id", groupPostId));
        List<User> users = groupPost.getLikePosts().stream().map(likePost -> likePost.getUser()).collect(Collectors.toList());
        List<UserDto> userDtoList = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());;
        return userDtoList;
    }

    @Override
    public Integer getNumberLikedOfPost(Integer groupPostId) {
        GroupPost groupPost = groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPostId", "Id", groupPostId));
        return groupPost.getNumberLikeOfPost();
    }

    @Override
    public String deleteLiked(Integer groupPostId, Integer userId) {
        GroupPost groupPost = groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPost", "Id", groupPostId));
        Optional<LikePost> likePostOptional = groupPost.getLikePosts().stream()
                .filter(likePost -> likePost.getUser().getId() == userId).findAny();
        if(likePostOptional.isPresent()) {
            LikePost likePost = likePostOptional.get();
            likePostRepo.delete(likePost);
            return "success";
        }
        return "failed";
    }

    @Override
    public String addLiked(Integer groupPostId, Integer userId) {
        GroupPost groupPost = groupPostRepo.findById(groupPostId)
                .orElseThrow(() -> new ResourceNotFoundException("GroupPost", "Id", groupPostId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Optional<LikePost> likePostOptional = groupPost.getLikePosts().stream()
                .filter(likePost -> likePost.getUser().getId() == userId).findAny();
        if(!likePostOptional.isPresent()) {
            LikePost likePost = new LikePost();
            likePost.setUser(user);
            likePost.setGroupPost(groupPost);
            return "success";
        }
        return "failed";
    }


    private GroupPost dtoToGroupPost(GroupPostDto groupPostDto) {
        return this.modelMapper.map(groupPostDto, GroupPost.class);
    }

    private GroupPostDto groupPostToDto(GroupPost groupPost) {
        return this.modelMapper.map(groupPost, GroupPostDto.class);
    }

    private Video dtoToVideo(VideoDto videoDto) {
        return this.modelMapper.map(videoDto, Video.class);
    }

    private Audio dtoToAudio(AudioDto audioDto) {
        return this.modelMapper.map(audioDto, Audio.class);
    }

    private Photo dtoToPhoto(PhotoDto photoDto) {
        return this.modelMapper.map(photoDto, Photo.class);
    }
}
