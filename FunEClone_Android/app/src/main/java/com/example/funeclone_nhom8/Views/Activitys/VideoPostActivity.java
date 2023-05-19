package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Adapters.CommentAdapter;
import com.example.funeclone_nhom8.Adapters.ReelAdapter;
import com.example.funeclone_nhom8.Datas.Apis.CommentApi;
import com.example.funeclone_nhom8.Datas.Apis.GroupApi;
import com.example.funeclone_nhom8.Datas.Apis.GroupPostApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.Comment;
import com.example.funeclone_nhom8.Datas.Models.Group;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Utils.IClickItemRoomListener;
import com.example.funeclone_nhom8.Utils.IClickOpenCommentListener;
import com.example.funeclone_nhom8.Utils.IClickReplyComment;
import com.example.funeclone_nhom8.Utils.IOnclickBackFromReel;
import com.example.funeclone_nhom8.ViewModel.CommentViewModel;
import com.example.funeclone_nhom8.Views.Fragments.RoomFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPostActivity extends AppCompatActivity {
    ReelAdapter reelAdapter;
    private Integer userId;
    private User user;
    private CommentAdapter commentAdapter;
    private CommentViewModel commentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_post);

        ViewPager2 video_viewpager = findViewById(R.id.video_viewpager);

        user = DataLocalManager.getUser();
        if(user != null) {
            userId = user.getId();
        }

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle != null) {
            Integer groupId = bundle.getInt("groupId");
            Integer videoId = bundle.getInt("videoId");
            GroupApi.groupApi.getAllVideoOfGroup(groupId).enqueue(new Callback<List<VideoItem>>() {
                @Override
                public void onResponse(Call<List<VideoItem>> call, Response<List<VideoItem>> response) {
                    List<VideoItem> videoItemList = response.body();
                    if(response.isSuccessful() && !videoItemList.isEmpty()) {
                        Optional<VideoItem> videoItemOptional = videoItemList.stream()
                                .filter(videoItem -> videoItem.getId() == videoId).findFirst();
                        if(videoItemOptional.isPresent()) {
                            VideoItem videoItem = videoItemOptional.get();
                            int index = videoItemList.indexOf(videoItem);
                            Collections.swap(videoItemList, index, 0);
                        }
                        reelAdapter = new ReelAdapter(VideoPostActivity.this, videoItemList, videoItem -> {
                            openBottomDialogComment(videoItem);
                        }, () -> {
                        });
                        video_viewpager.setAdapter(reelAdapter);

                    } else {
                        Toast.makeText(VideoPostActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<VideoItem>> call, Throwable t) {
                    Toast.makeText(VideoPostActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void openBottomDialogComment(VideoItem videoItem) {
        View viewDialog = getLayoutInflater().inflate(R.layout.layout_dialog_bottom_sheet_comment, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(VideoPostActivity.this);
        bottomSheetDialog.setContentView(viewDialog);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(true);

        RecyclerView rcv_comment = bottomSheetDialog.findViewById(R.id.rcv_comment);
        CircleImageView avatar = bottomSheetDialog.findViewById(R.id.avatar);
        EditText edt_comment = bottomSheetDialog.findViewById(R.id.edt_comment);
        Button btn_post = bottomSheetDialog.findViewById(R.id.btn_post);

        if(user != null) {
            Glide.with(bottomSheetDialog.getContext())
                    .load(ConstantUtil.BASE_URL+"user/image/"+user.getAvatar()).into(avatar);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_comment.setLayoutManager(linearLayoutManager);

        commentViewModel = new ViewModelProvider(VideoPostActivity.this).get(CommentViewModel.class);
        commentViewModel.getCommentLiveDataList().observe(VideoPostActivity.this, comments -> {
            commentAdapter = new CommentAdapter(bottomSheetDialog.getContext(), comments, comment -> {

            });
            rcv_comment.setAdapter(commentAdapter);
        });

        CommentApi.commentApi.getAllCommentOfGroupPost(3).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                for (Comment comment : response.body()) {
                    commentViewModel.addComment(comment);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

        String message = edt_comment.getText().toString().trim();
        btn_post.setOnClickListener(view -> {
            CommentApi.commentApi.createComment(message, videoItem.getGroupPost().getId(), userId).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if(response != null) {
                        commentViewModel.addComment(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                }
            });
        });

    }

    private List<VideoItem> getVideoData() {
        List<VideoItem> videoItemList = new ArrayList<>();

        VideoItem videoItem = new VideoItem();
        videoItem.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        videoItem.setVideoTitle("Big Buck Bunny");
//        videoItem.setVideoDescription("Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps");
        videoItemList.add(videoItem);

        VideoItem videoItem2 = new VideoItem();
        videoItem2.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
        videoItem2.setVideoTitle("Elephant Dream");
//        videoItem2.setVideoDescription("The first Blender Open Movie from 2006");
        videoItemList.add(videoItem2);

        VideoItem videoItem3 = new VideoItem();
        videoItem3.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        videoItem3.setVideoTitle("For Bigger Blazes");
//        videoItem3.setVideoDescription("HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV");
        videoItemList.add(videoItem3);
        return videoItemList;
    }
}