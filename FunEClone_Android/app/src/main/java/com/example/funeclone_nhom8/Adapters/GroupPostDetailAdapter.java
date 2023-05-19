package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Models.Audio;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupPostDetailAdapter extends RecyclerView.Adapter<GroupPostDetailAdapter.GroupPostDetailViewHolder> {

    private Context context;
    private List mediaCollection;

    public GroupPostDetailAdapter(Context context) {
        this.context = context;
        mediaCollection = new ArrayList();
    }

    public void setMediaCollection(List<?> mediaCollection) {
        this.mediaCollection = (List) Stream.
                concat(this.mediaCollection.stream(), mediaCollection.stream()).collect(Collectors.toList());
        notifyDataSetChanged();
    }

    public List getMediaCollection() {
        return mediaCollection;
    }

    @NonNull
    @Override
    public GroupPostDetailAdapter.GroupPostDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media_post_detail, parent, false);
        return new GroupPostDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPostDetailAdapter.GroupPostDetailViewHolder holder, int position) {
        Object media = mediaCollection.get(position);
        if(media instanceof Photo) {
            Glide.with(context)
                    .load(ConstantUtil.BASE_URL+"group_post/media/"+((Photo) media).getPhotoUrl())
                    .into(holder.preview_image);
        } else if(media instanceof Audio) {
            holder.preview_image.setImageResource(R.drawable.music_background);
        } else if(media instanceof VideoItem) {
            Log.e("PATH", ConstantUtil.BASE_URL+"group_post/video/"+((VideoItem) media).getVideoUrl());
            Glide.with(context)
                    .asBitmap()
                    .load(ConstantUtil.BASE_URL+"group_post/video/"+((VideoItem) media).getVideoUrl())
                    .into(holder.preview_image);
            try {
                String link = ((VideoItem) media).getVideoUrl();
                MediaController mediaController = new MediaController(context);
                mediaController.setAnchorView(holder.video_view);
                Uri videoUri = Uri.parse(link);
                holder.video_view.setMediaController(mediaController);
                holder.video_view.setVideoURI(videoUri);
                holder.preview_image.setOnClickListener(view -> {
                    holder.preview_image.setVisibility(View.GONE);
                    holder.video_view.start();
                });
                holder.video_view.setOnCompletionListener(mediaPlayer -> holder.preview_image.setVisibility(View.VISIBLE));
            } catch (Exception ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mediaCollection != null) {
            return mediaCollection.size();
        }
        return 0;
    }

    public class GroupPostDetailViewHolder extends RecyclerView.ViewHolder {
        private ImageView preview_image;
        private VideoView video_view;

        public GroupPostDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            preview_image = itemView.findViewById(R.id.preview_image);
            video_view = itemView.findViewById(R.id.video_view);
        }
    }
}
