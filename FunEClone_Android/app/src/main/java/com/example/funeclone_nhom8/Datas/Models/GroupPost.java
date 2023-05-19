package com.example.funeclone_nhom8.Datas.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GroupPost implements Serializable {
    private int id;
    private String message;
    private String datePost;
    private User user;
    private Group group;

    private List<Comment> comments;
    private List<Photo> photos;
    private List<VideoItem> videos;
    private List<Audio> audioSet;

    public GroupPost(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<VideoItem> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoItem> videos) {
        this.videos = videos;
    }

    public List<Audio> getAudioSet() {
        return audioSet;
    }

    public void setAudioSet(List<Audio> audioSet) {
        this.audioSet = audioSet;
    }

    @Override
    public String toString() {
        return "GroupPost{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", datePost=" + datePost +
                ", user=" + user +
                ", group=" + group +
                ", comments=" + comments +
                ", photos=" + photos +
                ", videos=" + videos +
                ", audioSet=" + audioSet +
                '}';
    }
}
