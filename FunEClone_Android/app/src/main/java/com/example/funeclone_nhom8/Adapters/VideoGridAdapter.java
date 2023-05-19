package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.funeclone_nhom8.Datas.Models.Audio;
import com.example.funeclone_nhom8.Datas.Models.GroupPost;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Views.Activitys.VideoPostActivity;
import com.example.funeclone_nhom8.Views.Fragments.PostDetailFragment;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VideoGridAdapter extends RecyclerView.Adapter<VideoGridAdapter.VideoViewHolder> {
    private Context context;
    private List mediaList;
    public VideoGridAdapter(Context context) {
        this.context = context;
        mediaList = new ArrayList();
    }

    public void setMediaList(List<?> mediaList) {
        this.mediaList = (List) Stream.concat(this.mediaList.stream(), mediaList.stream()).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoGridAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_grid, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Object object = mediaList.get(position);
        if(object instanceof Photo) {
            Glide.with(context)
                    .asBitmap()
                    .load(ConstantUtil.BASE_URL+"group_post/media/"+((Photo) object).getPhotoUrl())
                    .apply(new RequestOptions().override(300,200))
                    .into(holder.preview_image);
            holder.item_video_grid.setOnClickListener(view -> {
                onClickToPostDetail(((Photo) object).getGroupPost().getId(), view);
            });
        } else if(object instanceof Audio) {
            holder.preview_image.setImageResource(R.drawable.music_background);
            holder.item_video_grid.setOnClickListener(view -> {
                onClickToPostDetail(((Audio) object).getGroupPost().getId(), view);
            });
        } else if(object instanceof VideoItem) {
            Glide.with(context)
                    .asBitmap()
                    .load(ConstantUtil.BASE_URL+"group_post/media/"+((VideoItem) object).getVideoUrl())
                    .apply(new RequestOptions().override(300,200))
                    .into(holder.preview_image);
            holder.item_video_grid.setOnClickListener(view -> {
                onClickToReel(((VideoItem) object).getGroupPost().getGroup().getId(), ((VideoItem) object).getId());
            });
        }

//        Log.e("MIMETYPE", URLConnection.guessContentTypeFromName(
//                ConstantUtil.BASE_URL+"group_post/media/"+videoItem.getVideoUrl()));

//        try {
//            String link = videoItem.videUrl;
//            MediaController mediaController = new MediaController(context);
//            mediaController.setAnchorView(holder.video_view);
//            Uri videoUri = Uri.parse(link);
//            holder.video_view.setMediaController(mediaController);
//            holder.video_view.setVideoURI(videoUri);
//            holder.preview_image.setOnClickListener(view -> {
//                holder.preview_image.setVisibility(View.GONE);
//                holder.video_view.start();
//            });
//            holder.video_view.setOnCompletionListener(mediaPlayer -> holder.preview_image.setVisibility(View.VISIBLE));
//        } catch (Exception e) {
//            Toast.makeText(context, "Error connection", Toast.LENGTH_SHORT).show();
//        }
    }

    private String getMimeTypeOfUrl(String url) {
        String link = ConstantUtil.BASE_URL+"group_post/media/"+url;
        return URLConnection.guessContentTypeFromName(link);
    }

    private void onClickToPostDetail(Integer groupPostId, View view) {
        PostDetailFragment postDetailFragment = new PostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("groupPostId", groupPostId);
        postDetailFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((FragmentActivity) view.getContext())
                .getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_nav, postDetailFragment);
        fragmentTransaction.commit();
    }

    private void onClickToReel(Integer groupId, Integer videoId) {
        Intent intent = new Intent(context, VideoPostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("groupId", groupId);
        bundle.putInt("videoId", videoId);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(mediaList != null) {
            return mediaList.size();
        }
        return 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView preview_image;
//        private VideoView video_view;
        private ConstraintLayout item_video_grid;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            preview_image = itemView.findViewById(R.id.preview_image);
//            video_view = itemView.findViewById(R.id.video_view);
            item_video_grid = itemView.findViewById(R.id.item_view_grid);
        }
    }
}
