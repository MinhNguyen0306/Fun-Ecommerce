package com.example.funeclone_nhom8.Views.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Adapters.CommentAdapter;
import com.example.funeclone_nhom8.Adapters.GroupPostDetailAdapter;
import com.example.funeclone_nhom8.Datas.Apis.CommentApi;
import com.example.funeclone_nhom8.Datas.Apis.GroupPostApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.Audio;
import com.example.funeclone_nhom8.Datas.Models.Comment;
import com.example.funeclone_nhom8.Datas.Models.GroupPost;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.AppUtil;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.ViewModel.CommentViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailFragment extends Fragment {

    private CircleIndicator3 circleIndicator3;
    private CircleImageView avatar;
    private ViewPager2 view_pager_2;
    private EditText edt_comment;
    private TextView reply_to_user;
    private TextView cancel;
    private LinearLayout reply_action_bar;
    private RecyclerView rcv_comment;
    private Button btn_send_comment;
    private CommentAdapter commentAdapter;
    private int groupPostId;
    private int userId;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container,false);

        initUI(view);

        Bundle bundle = getArguments();
        if(bundle != null) {
            groupPostId = bundle.getInt("groupPostId");
        }

        user = DataLocalManager.getUser();
        if(user != null) {
            userId = user.getId();
            Glide.with(getContext())
                    .load(ConstantUtil.BASE_URL+"user/image/"+user.getAvatar()).into(avatar);
        }

        GroupPostDetailAdapter groupPostDetailAdapter = new GroupPostDetailAdapter(getContext());
        view_pager_2.setAdapter(groupPostDetailAdapter);

        GroupPostApi.groupPostApi.getAllPhotoOfGroupPost(groupPostId).enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                List<Photo> photoList = response.body();
                if(photoList != null && !photoList.isEmpty()) {
                    groupPostDetailAdapter.setMediaCollection(photoList);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.e("GetAllPhotoOfGroupPost Error", t.getMessage());
            }
        });

        GroupPostApi.groupPostApi.getAllAudioOfGroupPost(groupPostId).enqueue(new Callback<List<Audio>>() {
            @Override
            public void onResponse(Call<List<Audio>> call, Response<List<Audio>> response) {
                List<Audio> audioList = response.body();
                if(audioList != null && !audioList.isEmpty()) {
                    groupPostDetailAdapter.setMediaCollection(audioList);
                }
            }

            @Override
            public void onFailure(Call<List<Audio>> call, Throwable t) {
                Log.e("GetAllAudioOfGroupPost Error", t.getMessage());
            }
        });

        GroupPostApi.groupPostApi.getAllVideoOfGroupPost(groupPostId).enqueue(new Callback<List<VideoItem>>() {
            @Override
            public void onResponse(Call<List<VideoItem>> call, Response<List<VideoItem>> response) {
                List<VideoItem> videoItemList = response.body();
                if(videoItemList != null && !videoItemList.isEmpty()) {
                    groupPostDetailAdapter.setMediaCollection(videoItemList);
                }
            }

            @Override
            public void onFailure(Call<List<VideoItem>> call, Throwable t) {
                Log.e("GetAllVideoOfGroupPost Error", t.getMessage());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_comment.setLayoutManager(linearLayoutManager);

        CommentViewModel commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        commentViewModel.getCommentLiveDataList().observe(getViewLifecycleOwner(), comments -> {
            commentAdapter = new CommentAdapter(getContext(), comments, comment -> {
                AppUtil.openKeyBoard(getActivity(), edt_comment);
                edt_comment.requestFocus();
                reply_action_bar.setVisibility(View.VISIBLE);
                reply_to_user.setText(comment.getUser().getName());
                btn_send_comment.setOnClickListener(view1 -> {
                    String message = edt_comment.getText().toString().trim();
                    CommentApi.commentApi.createChildComment(message, groupPostId, userId, comment.getId()).enqueue(new Callback<Comment>() {
                        @Override
                        public void onResponse(Call<Comment> call, Response<Comment> response) {
                            if(response != null && response.isSuccessful()) {
                                Comment childComment = response.body();
                                commentAdapter.addChildComment(childComment);
                            } else {
                                Toast.makeText(getContext(), "Create Reply Error", Toast.LENGTH_SHORT).show();
                            }
                            AppUtil.closeKeyBoard(getActivity(), edt_comment);
                            reply_to_user.setText("");
                            reply_action_bar.setVisibility(View.GONE);
                            edt_comment.clearFocus();
                        }

                        @Override
                        public void onFailure(Call<Comment> call, Throwable t) {
                            Toast.makeText(getContext(), "Call Api Failed", Toast.LENGTH_SHORT).show();
                            AppUtil.closeKeyBoard(getActivity(), edt_comment);
                            reply_to_user.setText("");
                            reply_action_bar.setVisibility(View.GONE);
                            edt_comment.clearFocus();
                        }
                    });
                });
                cancel.setOnClickListener(view1 -> {
                    AppUtil.closeKeyBoard(getActivity(), edt_comment);
                    reply_to_user.setText("");
                    reply_action_bar.setVisibility(View.GONE);
                    edt_comment.clearFocus();
                });
            });
            rcv_comment.setAdapter(commentAdapter);
        });

        CommentApi.commentApi.getAllCommentOfGroupPost(groupPostId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.body() != null && !response.body().isEmpty()) {
                    for(Comment comment : response.body()) {
                        commentViewModel.addComment(comment);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("getAllCommentOfGroupPost Error", t.getMessage());
                Toast.makeText(getContext(), "Get Comments Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_send_comment.setOnClickListener(view1 -> {
            String message = edt_comment.getText().toString().trim();
            CommentApi.commentApi.createComment(message, groupPostId, userId).enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    Comment comment = response.body();
                    if(comment != null) {
                        commentViewModel.addComment(comment);
                    }
                    AppUtil.closeKeyBoard(getActivity(), edt_comment);
                    edt_comment.clearFocus();
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Log.e("CreateComment Error", t.getMessage());
                    AppUtil.closeKeyBoard(getActivity(), edt_comment);
                }
            });
        });

        return view;
    }


    private void initUI(View view) {
        avatar = view.findViewById(R.id.avatar);
        circleIndicator3 = view.findViewById(R.id.circle_indicator);
        view_pager_2 = view.findViewById(R.id.view_pager_2);
        edt_comment = view.findViewById(R.id.edt_comment);
        btn_send_comment = view.findViewById(R.id.btn_send_comment);
        rcv_comment = view.findViewById(R.id.rcv_comment);
        reply_to_user = view.findViewById(R.id.reply_to_user);
        reply_action_bar = view.findViewById(R.id.reply_action_bar);
        cancel = view.findViewById(R.id.cancel);
    }
}
