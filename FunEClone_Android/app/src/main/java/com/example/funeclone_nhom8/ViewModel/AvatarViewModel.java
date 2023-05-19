package com.example.funeclone_nhom8.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funeclone_nhom8.Datas.Models.User;

import java.util.ArrayList;
import java.util.List;

public class AvatarViewModel extends ViewModel {
    private MutableLiveData<List<User>> userLiveData;
    private List<User> users;

    public AvatarViewModel() {
        userLiveData = new MutableLiveData<>();
        users = new ArrayList<>();
    }

    public MutableLiveData<List<User>> getUserLiveData() {
        return userLiveData;
    }
    public void addUser(User user) {
        users.add(user);
        userLiveData.setValue(users);
    }

    public List<User> getUsers() {
        return users;
    }
}
