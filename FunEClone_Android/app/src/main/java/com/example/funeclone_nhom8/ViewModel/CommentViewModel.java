package com.example.funeclone_nhom8.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funeclone_nhom8.Datas.Models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentViewModel extends ViewModel {
    private MutableLiveData<List<Comment>> commentLiveDataList;
    private List<Comment> commentList;

    public CommentViewModel() {
        commentList = new ArrayList<>();
        commentLiveDataList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Comment>> getCommentLiveDataList() {
        return commentLiveDataList;
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
        commentLiveDataList.setValue(commentList);
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
