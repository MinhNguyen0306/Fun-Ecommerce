package com.example.funeclone_nhom8.Views.Activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.funeclone_nhom8.Datas.Apis.UserApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    private static final int MY_REQUEST_CODE_CAMERA = 100;
    private RelativeLayout btnDateOfBirth;
    private RelativeLayout btnGender;
    private DatePicker datePickerDateOfBirth;
    private LinearLayout chooseGender;
    private ImageView down_date_picker_profile;
    private ImageView down_gender_profile;
    private Button btn_open_camera;
    private Button btn_update_profile;
    private Button btn_open_gallery;
    private RelativeLayout btn_open_avatar;
    private CircleImageView avatar;
    private ImageView cover_image;
    private EditText edt_name_edit_profile;
    private ProgressDialog progressDialog;

    private boolean isShowDatePicker = false;
    private boolean isShowChooseGender = false;
    private Uri mUriAvatar;
    private Uri mUriCoverAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initUI();

        // Init progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        btn_update_profile.setOnClickListener(view -> {
            if(mUriAvatar != null) {
                onCLickCallApiUpdateProfile();
            }
        });

        btnDateOfBirth.setOnClickListener(view -> {
            isShowDatePicker = !isShowDatePicker;
            if(isShowDatePicker == true){
                datePickerDateOfBirth.setVisibility(View.VISIBLE);
                down_date_picker_profile.setRotationX(180);
            }
            else {
                datePickerDateOfBirth.setVisibility(View.GONE);
                down_date_picker_profile.setRotationX(0);
            }
        });

        btnGender.setOnClickListener(view -> {
            isShowChooseGender = !isShowChooseGender;
            if(isShowChooseGender) {
                chooseGender.setVisibility(View.VISIBLE);
                down_gender_profile.setRotationX(180);
            } else {
                chooseGender.setVisibility(View.GONE);
                down_gender_profile.setRotationX(0);
            }
        });

        btn_open_avatar.setOnClickListener(view -> {
            onClickRequestPermission();
        });

        btn_open_gallery.setOnClickListener(view -> {
            onClickRequestPermissionCoverAvatar();
        });

        btn_open_camera.setOnClickListener(view -> {
            onClickRequestPermissionCamera();
        });
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUriAvatar = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            avatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> activityResultLauncherCoverAvatar = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUriCoverAvatar = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            cover_image.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> activityResultLauncherCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null) {
                            return;
                        }
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        cover_image.setImageBitmap(bitmap);

                        mUriCoverAvatar = getImageUri(getApplicationContext(), bitmap);
                    }
                }
            }
    );

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Bitmap outImage = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), outImage, "Title", null);
        return Uri.parse(path);
    }

    private void onCLickCallApiUpdateProfile() {
        progressDialog.show();

        String name = edt_name_edit_profile.getText().toString().trim();

        String realPathAvatar = RealPathUtil.getRealPath(this, mUriAvatar);
        String realPathCoverAvatar = RealPathUtil.getRealPath(this, mUriCoverAvatar);

        File fileAvatar = new File(realPathAvatar);
        File fileCoverAvatar = new File(realPathCoverAvatar);

        RequestBody requestBodyUserName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestBodyAvatar = RequestBody.create(MediaType.parse("multipart/form-data"), fileAvatar);
        RequestBody requestBodyCoverAvatar = RequestBody.create(MediaType.parse("multipart/form-data"), fileCoverAvatar);

        MultipartBody.Part multipartBodyAvatar = MultipartBody.Part.createFormData("avatar", fileAvatar.getName(), requestBodyAvatar);
        MultipartBody.Part multipartBodyCoverAvatar = MultipartBody.Part.createFormData("coverAvatar", fileCoverAvatar.getName(), requestBodyCoverAvatar);

        UserApi.userApi.updateUser(requestBodyUserName, multipartBodyAvatar, multipartBodyCoverAvatar, 2).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss();
                User userResponse = response.body();
                if(userResponse != null) {
                    Log.e("USER", userResponse.toString());
                    DataLocalManager.setUser(userResponse);
                    switchProfileActivity();
                    Toast.makeText(EditProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                switchProfileActivity();
                Toast.makeText(EditProfileActivity.this, "Call Api Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchProfileActivity() {
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void initUI() {
        btnDateOfBirth = findViewById(R.id.btn_date_of_birth);
        btnGender = findViewById(R.id.btn_gender);
        datePickerDateOfBirth = findViewById(R.id.date_picker_profile);
        chooseGender = findViewById(R.id.choose_gender);
        down_date_picker_profile = findViewById(R.id.down_date_picker_profile);
        down_gender_profile = findViewById(R.id.down_gender_profile);
        btn_open_camera = findViewById(R.id.btn_open_camera);
        btn_open_avatar = findViewById(R.id.btn_open_avatar);
        btn_open_gallery = findViewById(R.id.btn_open_gallery);
        btn_update_profile = findViewById(R.id.btn_update_profile);
        avatar = findViewById(R.id.image_profile);
        cover_image = findViewById(R.id.cover_image_profile);
        edt_name_edit_profile = findViewById(R.id.edt_name_edit_profile);
    }
    private void onClickRequestPermissionCamera() {
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            String[] permission = {Manifest.permission.CAMERA};
            requestPermissions(permission, MY_REQUEST_CODE_CAMERA);
        }
    }

    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void onClickRequestPermissionCoverAvatar() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGalleryCoverAvatar();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGalleryCoverAvatar();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else if(requestCode == MY_REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void openGalleryCoverAvatar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncherCoverAvatar.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncherCamera.launch(Intent.createChooser(intent, "Capture Image"));
    }

}