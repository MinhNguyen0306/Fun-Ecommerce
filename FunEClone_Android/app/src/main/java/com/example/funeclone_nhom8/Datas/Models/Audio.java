package com.example.funeclone_nhom8.Datas.Models;

import java.io.Serializable;

public class Audio implements Serializable {
    private int id;
    private String audioName;
    private String audioUrl;
    private GroupPost groupPost;

    public Audio() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public GroupPost getGroupPost() {
        return groupPost;
    }

    public void setGroupPost(GroupPost groupPost) {
        this.groupPost = groupPost;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "id=" + id +
                ", audioName='" + audioName + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", groupPost=" + groupPost +
                '}';
    }
}
