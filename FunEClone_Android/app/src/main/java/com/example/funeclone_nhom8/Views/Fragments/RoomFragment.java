package com.example.funeclone_nhom8.Views.Fragments;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Adapters.AddPostAdapter;
import com.example.funeclone_nhom8.Adapters.RoomDetailViewPagerAdapter;
import com.example.funeclone_nhom8.Adapters.VideoAdapter;
import com.example.funeclone_nhom8.Datas.Apis.GroupPostApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.Group;
import com.example.funeclone_nhom8.Datas.Models.GroupPost;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Utils.RealPathUtil;
import com.example.funeclone_nhom8.ViewModel.ImageViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomFragment extends Fragment {
    public static final String TAG = RoomFragment.class.getName();

    private static final int MY_REQUEST_CODE = 10;
    private static final int MY_REQUEST_CODE_CAMERA = 100;
    private FloatingActionButton btn_open_room_dialog;
    private CircleImageView avatar;
    private TextView title;
    private TextView cart_count;
    private ImageButton btn_back;
    private TabLayout tab_layout;
    private ViewPager2 viewPager2;
    private List<Uri> uriList =  new ArrayList<>();
    private ImageViewModel imageViewModel;
    private int index = 0;
    private int groupId;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        initUI(view);

        Integer cartCount = DataLocalManager.getCountCart();
        if(cartCount > 0) {
            cart_count.setVisibility(View.VISIBLE);
            cart_count.setText(String.valueOf(cartCount));
        } else {
            cart_count.setVisibility(View.GONE);
        }

        user = DataLocalManager.getUser();

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null) {
            Group group = (Group) bundleReceive.get("group");
            if(group != null) {
                title.setText(group.getTitle());
                Glide.with(getContext()).load(ConstantUtil.BASE_URL+"group/image/"+group.getAvatar()).into(avatar);
                groupId = group.getId();
            }
        }

        RoomDetailViewPagerAdapter roomDetailViewPagerAdapter = new RoomDetailViewPagerAdapter(getActivity(), groupId);
        viewPager2.setAdapter(roomDetailViewPagerAdapter);
        new TabLayoutMediator(tab_layout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.grid);
                    break;
                case 1:
                    tab.setIcon(R.drawable.mine);
                    break;
            }
        }).attach();

        btn_open_room_dialog.setOnClickListener(view1 -> openHomeActionDialog(Gravity.CENTER));
        btn_back.setOnClickListener(view1 -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.content_nav, new RoomManageFragment());
            fragmentTransaction.commit();
        });

        return view;
    }

    private void openHomeActionDialog(int gravity) {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_room_action);

        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        RelativeLayout openAddProduct = dialog.findViewById(R.id.open_add_pic_video);
        ImageButton closeDialog = dialog.findViewById(R.id.btn_close_home_action_dialog);

        closeDialog.setOnClickListener(view -> dialog.dismiss());

        openAddProduct.setOnClickListener(view -> {
            openAddPicVideoRoomDialog(Gravity.CENTER);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void openAddPicVideoRoomDialog(int gravity) {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_add_pic_video_room);

        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        ImageButton closeDialog = dialog.findViewById(R.id.btn_close_home_action_dialog);
        closeDialog.setOnClickListener(view -> dialog.dismiss());

        RecyclerView rcvLiveData = dialog.findViewById(R.id.rcv_live_data);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        rcvLiveData.setLayoutManager(gridLayoutManager);

        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        imageViewModel.getImageLiveDataList().observe(getViewLifecycleOwner(), bitmaps -> {
            AddPostAdapter addPostAdapter = new AddPostAdapter(bitmaps);
            rcvLiveData.setAdapter(addPostAdapter);
        });

        ImageButton add_live_data = dialog.findViewById(R.id.add_live_data);
        add_live_data.setOnClickListener(view -> {
            onClickOpenBottomSheetGallery(imageViewModel);
            index++;
        });

        EditText edt_description = dialog.findViewById(R.id.edt_description);
        Button btn_post = dialog.findViewById(R.id.btn_post);
        btn_post.setOnClickListener(view -> {
            String description = edt_description.getText().toString().trim();

            List<MultipartBody.Part> multipartList = new ArrayList<>();

            uriList.stream().forEach(uri -> {
                String realPathImage = RealPathUtil.getRealPath(this.getContext(), uri);
                File fileProductImage = new File(realPathImage);
                RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), fileProductImage);
                MultipartBody.Part multipartBodyImage = MultipartBody.Part.
                        createFormData("media", fileProductImage.getName(), requestBodyImage);
                multipartList.add(multipartBodyImage);
            });

            GroupPostApi.groupPostApi.createGroupPost(description, groupId,  multipartList, user.getId()).enqueue(new Callback<GroupPost>() {
                @Override
                public void onResponse(Call<GroupPost> call, Response<GroupPost> response) {
                    if(response.body() != null) {
                        Toast.makeText(getContext(), "Upload Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
                @Override
                public void onFailure(Call<GroupPost> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }

    private void onClickOpenBottomSheetGallery(ImageViewModel imageViewModel) {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_dialog_choose_image, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(false);

        Button btn_cancel = bottomSheetDialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        Button btn_open_gallery = bottomSheetDialog.findViewById(R.id.btn_open_gallery);
        btn_open_gallery.setOnClickListener(view -> {
            openGallery();
            bottomSheetDialog.dismiss();
        });

        Button btn_open_camera = bottomSheetDialog.findViewById(R.id.btn_open_camera);
        btn_open_camera.setOnClickListener(view -> {
            openCamera();
            bottomSheetDialog.dismiss();
        });
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Bitmap outImage = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), outImage, "Title", null);
        return Uri.parse(path);
    }

    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void onClickRequestPermissionCamera() {
        if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            String[] permission = {Manifest.permission.CAMERA};
            requestPermissions(permission, MY_REQUEST_CODE_CAMERA);
        }
    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if(result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if(data == null) {
                return;
            }
            Uri uri = data.getData();
            uriList.add(uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imageViewModel.addImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

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
                imageViewModel.addImage(bitmap);

                uriList.add(getImageUri(getActivity().getApplicationContext(), bitmap));
            }
        }
    }
        );

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

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncherCamera.launch(Intent.createChooser(intent, "Capture Image"));
    }

    private void initUI(View view) {
        btn_open_room_dialog = view.findViewById(R.id.btn_open_room_dialog);
        avatar = view.findViewById(R.id.avatar);
        title = view.findViewById(R.id.title);
        btn_back = view.findViewById(R.id.btn_back);
        tab_layout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager_2);
        cart_count = view.findViewById(R.id.cart_count);
    }
}
