package com.example.funeclone_nhom8.Datas.Models;

import java.io.Serializable;

public class Group implements Serializable {
    private int id;
    private String title;
    private String avatar;

    public Group(int id, String title, String avatar) {
        this.id = id;
        this.title = title;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
