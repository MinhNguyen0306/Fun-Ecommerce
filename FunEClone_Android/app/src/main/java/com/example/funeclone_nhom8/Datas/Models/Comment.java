package com.example.funeclone_nhom8.Datas.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable {
    private int id;
    private String message;
    private String createAt;
    private Comment parentComment;
    private User user;

    public Comment() {}

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createAt='" + createAt + '\'' +
                ", user=" + user +
                '}';
    }
}
