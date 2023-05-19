package com.example.funeclone_nhom8.Datas.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoItem implements Serializable {
    private int id;
    private String videoUrl;
    @SerializedName("videoName")
    private String videoTitle;
    private GroupPost groupPost;

    public VideoItem(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

//    public String getVideoDescription() {
//        return videoDescription;
//    }
//
//    public void setVideoDescription(String videoDescription) {
//        this.videoDescription = videoDescription;
//    }

    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
    }

    @Override
    public String toString() {
        return "VideoItem{" +
                "id=" + id +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", groupPost=" + groupPost +
                '}';
    }
}
