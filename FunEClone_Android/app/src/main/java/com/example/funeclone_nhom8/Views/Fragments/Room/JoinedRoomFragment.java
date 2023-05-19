package com.example.funeclone_nhom8.Views.Fragments.Room;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Adapters.AddUserAdapter;
import com.example.funeclone_nhom8.Adapters.RoomAdapter;
import com.example.funeclone_nhom8.Adapters.SearchUserResultAdapter;
import com.example.funeclone_nhom8.Datas.Apis.GroupApi;
import com.example.funeclone_nhom8.Datas.Apis.UserApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.Group;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.AppUtil;
import com.example.funeclone_nhom8.Utils.IClickItemHintUserListener;
import com.example.funeclone_nhom8.Utils.IClickItemRoomListener;
import com.example.funeclone_nhom8.Utils.RealPathUtil;
import com.example.funeclone_nhom8.ViewModel.AvatarViewModel;
import com.example.funeclone_nhom8.Views.Fragments.RoomFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinedRoomFragment extends Fragment {
    public static final int MY_REQUEST_CODE = 10;
    public static final int MY_REQUEST_CODE_CAMERA = 100;
    private RoomAdapter roomAdapter;
    private ImageView image_room;
    private Uri mUriData;
    private Set<User> groupFollowers = new HashSet<>();
    private User user;
    public JoinedRoomFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joined_room, container, false);

        user = DataLocalManager.getUser();

        RecyclerView rcv_room = view.findViewById(R.id.rcv_room);
        FloatingActionButton btn_open_dialog = view.findViewById(R.id.btn_open_dialog);

        roomAdapter = new RoomAdapter(this.getContext(), group -> {
            goToRoomFragment(group);
        });

        if(user != null) {
            GroupApi.groupApi.getAllGroupOfUser(1).enqueue(new Callback<List<Group>>() {
                @Override
                public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                    if(response != null) {
                        roomAdapter.setData(response.body());
                        roomAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<Group>> call, Throwable t) {
                }
            });
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        rcv_room.setLayoutManager(linearLayoutManager);
        rcv_room.addItemDecoration(itemDecoration);
        rcv_room.setAdapter(roomAdapter);

        btn_open_dialog.setOnClickListener(view1 -> {
            openDialog1(Gravity.CENTER);
        });

        return view;
    }

    private void goToRoomFragment(Group group) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        RoomFragment roomFragment = new RoomFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("group", group);
        roomFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_nav, roomFragment);
        fragmentTransaction.addToBackStack(RoomFragment.TAG);
        fragmentTransaction.commit();
    }

    private void openDialog1(int gravity) {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_room);

        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        RelativeLayout openAddRoom = dialog.findViewById(R.id.open_add_room);
        ImageButton closeDialog = dialog.findViewById(R.id.btn_close);

        closeDialog.setOnClickListener(view -> dialog.dismiss());

        openAddRoom.setOnClickListener(view -> {
            openDialogAddRoom(gravity);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void openDialogAddRoom(int gravity) {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_room);

        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        EditText edt_room_name = dialog.findViewById(R.id.edt_room_name);
        EditText edt_search_user = dialog.findViewById(R.id.edt_search_user);
        image_room = dialog.findViewById(R.id.image_room);
        RecyclerView rcv_user = dialog.findViewById(R.id.rcv_user);
        RecyclerView rcv_hint_user = dialog.findViewById(R.id.rcv_hint_user);
        Button btn_open_gallery = dialog.findViewById(R.id.btn_open_gallery);
        Button btn_open_camera = dialog.findViewById(R.id.btn_open_camera);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_create = dialog.findViewById(R.id.btn_create);
        ImageButton closeDialog = dialog.findViewById(R.id.btn_close);

        String roomName = edt_room_name.getText().toString().trim();
        String searchKey = edt_search_user.getText().toString().trim();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        rcv_user.setLayoutManager(gridLayoutManager);

        AvatarViewModel avatarViewModel = new ViewModelProvider(this).get(AvatarViewModel.class);
        avatarViewModel.getUserLiveData().observe(getViewLifecycleOwner(), users -> {
            AddUserAdapter addUserAdapter = new AddUserAdapter(getContext(), users, user -> {
                groupFollowers.remove(user);
            });
            rcv_user.setAdapter(addUserAdapter);
        });

        List<User> userHints = new ArrayList<>();
        SearchUserResultAdapter searchUserResultAdapter = new SearchUserResultAdapter(getContext(), userHints, user -> {
            avatarViewModel.addUser(user);
            /**********   ADD USERS   **********/
            groupFollowers.add(user);
            rcv_hint_user.setVisibility(View.GONE);
            AppUtil.closeKeyBoard(getActivity(), edt_search_user);
            edt_search_user.clearFocus();
            edt_search_user.setText("");
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv_hint_user.setLayoutManager(linearLayoutManager);
        rcv_hint_user.setAdapter(searchUserResultAdapter);

        edt_search_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rcv_hint_user.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(edt_search_user.getText().toString().matches("")){
                    rcv_hint_user.setVisibility(View.GONE);
                } else {
                    UserApi.userApi.searchUsers(edt_search_user.getText().toString().trim()).enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            if(response != null && !response.body().isEmpty()) {
                                searchUserResultAdapter.filteredList(response.body());
                            }
                        }
                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                        }
                    });
                }
            }
        });

        btn_open_gallery.setOnClickListener(view -> onClickRequestPermission());
        btn_open_camera.setOnClickListener(view -> onClickRequestPermissionCamera());
        closeDialog.setOnClickListener(view -> dialog.dismiss());
        btn_cancel.setOnClickListener(view -> dialog.dismiss());
        btn_create.setOnClickListener(view -> {
            String realPathImage = RealPathUtil.getRealPath(this.getContext(), mUriData);
            File fileProductImage = new File(realPathImage);

            RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), fileProductImage);
            MultipartBody.Part multipartBodyImage = MultipartBody.Part.
                    createFormData("avatar", fileProductImage.getName(), requestBodyImage);

            Set<MultipartBody.Part> multipartSetUser = new HashSet<>();

            groupFollowers.stream().forEach(user1 -> {
                RequestBody requestBodyProduct = RequestBody.create(MediaType.parse("application/json"), user1.toJson());
                MultipartBody.Part multipartBodyProduct = MultipartBody.Part
                        .createFormData("users",user1.toJson(), requestBodyProduct);
                multipartSetUser.add(multipartBodyProduct);
            });

            if(user != null) {
                GroupApi.groupApi.createGroup(user.getId() , roomName, multipartBodyImage, multipartSetUser).enqueue(new Callback<Group>() {
                    @Override
                    public void onResponse(Call<Group> call, Response<Group> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), "Create Group Success", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Create Group Failed", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Group> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    /********** OPEN CAMERA HANDLE ********/
    private void onClickRequestPermissionCamera() {
        if(getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            onClickOpenCamera();
        } else {
            String[] permission = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(getActivity(),permission, MY_REQUEST_CODE_CAMERA);
        }
    }
    private void onClickOpenCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncherCamera.launch(Intent.createChooser(intent, "Capture Image"));
    }
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
                        image_room.setImageBitmap(bitmap);
                        image_room.setMaxHeight(200);

                        mUriData = getImageUri(getContext().getApplicationContext(), bitmap);
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
    /**********   END OPEN CAMERA HANDLE   **********/



    /**********   OPEN GALLERY HANDLE   **********/
    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onclickOpenGallery();
            return;
        }
        if(getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            onclickOpenGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(getActivity(),permission, MY_REQUEST_CODE);
        }
    }
    private void onclickOpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
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
                        mUriData = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            image_room.setImageBitmap(bitmap);
                            image_room.setMaxHeight(200);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onclickOpenGallery();
            }
        } else if(requestCode == MY_REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onClickOpenCamera();
            }
        }
    }
    /**********   END OPEN GALLERY HANDLE   **********/
}
