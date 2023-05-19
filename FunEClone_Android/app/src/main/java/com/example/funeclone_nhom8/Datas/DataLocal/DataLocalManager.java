package com.example.funeclone_nhom8.Datas.DataLocal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.funeclone_nhom8.Datas.Apis.CartApi;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataLocalManager {
    public static final String PREF_OBJECT_USER = "PREF_OBJECT_USER";
    public static final String PREF_SIZE_CART= "PREF_SIZE_CART";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if(instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setUser(User user) {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_USER, jsonUser);
    }

    public static User getUser() {
        String jsonUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_USER);
        Gson gson = new Gson();
        User user = gson.fromJson(jsonUser, User.class);
        return user;
    }

    public static void deleteUser() {
        DataLocalManager.getInstance().mySharedPreferences.deleteStringValue();
    }

    public static void setCountCart(Integer countCart) {
        DataLocalManager.getInstance().mySharedPreferences.putIntegerValue(PREF_SIZE_CART, countCart);
    }

    public static Integer getCountCart() {
        Integer countCart = DataLocalManager.getInstance().mySharedPreferences.getIntegerValue(PREF_SIZE_CART);
        return countCart;
    }
}
