package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;

import java.util.Optional;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout open_edit_profile_activity;
    private CircleImageView avatar;
    private LinearLayout sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();

        Optional<User> userOptional = Optional.ofNullable(DataLocalManager.getUser());
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            String urlAvatar = "http://"+ ConstantUtil.IPV4+":9090/api/v1/user/image/" + user.getAvatar();

            Glide.with(ProfileActivity.this).load(urlAvatar).into(avatar);
        }

        open_edit_profile_activity.setOnClickListener(view -> openEditProfileActivity());

        sign_out.setOnClickListener(view -> {
            DataLocalManager.deleteUser();
            Toast.makeText(ProfileActivity.this, "Sign out!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, SigninActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initUI() {
        open_edit_profile_activity = findViewById(R.id.open_edit_profile_activity);
        avatar = findViewById(R.id.avatar);
        sign_out = findViewById(R.id.sign_out);
    }

    private void openEditProfileActivity() {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }
}