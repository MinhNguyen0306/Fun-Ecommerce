package com.example.funeclone_nhom8;

import android.app.Application;

import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
