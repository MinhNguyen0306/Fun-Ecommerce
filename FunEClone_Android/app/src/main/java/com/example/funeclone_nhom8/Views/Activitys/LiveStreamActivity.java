package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;

import fi.vtt.nubomedia.kurentoroomclientandroid.KurentoRoomAPI;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomError;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomListener;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomNotification;
import fi.vtt.nubomedia.kurentoroomclientandroid.RoomResponse;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;

public class LiveStreamActivity extends AppCompatActivity implements RoomListener {
    private LooperExecutor executor;
    private static KurentoRoomAPI kurentoRoomAPI;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stream);

        user = DataLocalManager.getUser();

        executor = new LooperExecutor();
        executor.requestStart();
        String serverWebSocket = "http://192.168.1.4:9090/livestream";
        kurentoRoomAPI = new KurentoRoomAPI(executor, serverWebSocket, this);
        kurentoRoomAPI.connectWebSocket();

        initUI();

    }
    private void initUI() {
    }

    @Override
    public void onRoomResponse(RoomResponse response) {
        if(response.getId() == 123) {
            Log.e("TAG", "Đã kết nối thành công với phòng!");
            kurentoRoomAPI.sendMessage(user.getName(), "Room1", "Hello", 125);
        }
    }

    @Override
    public void onRoomError(RoomError error) {
        Log.e("Error", error.toString());
    }

    @Override
    public void onRoomNotification(RoomNotification notification) {
        if(notification.getMethod().equals(RoomListener.METHOD_SEND_MESSAGE)) {
            final String username = notification.getParam("user").toString();
            final String message = notification.getParam("message").toString();
            Log.e("Message", "Oh boy! " + username + " sent me a message: " + message);
        }
    }

    @Override
    public void onRoomConnected() {
        kurentoRoomAPI.sendJoinRoom(user.getName(), "Room1", true, 123);
    }

    @Override
    public void onRoomDisconnected() {

    }
}