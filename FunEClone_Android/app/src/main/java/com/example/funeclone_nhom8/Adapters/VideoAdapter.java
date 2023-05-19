package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private List<VideoItem> videoItems;

    public VideoAdapter(Context context, List<VideoItem> videoItems) {
        this.context = context;
        this.videoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        VideoItem videoItem = videoItems.get(position);

        Glide.with(context)
                .asBitmap()
                .load(videoItem.getVideoUrl())
                .into(holder.preview_image);

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

    @Override
    public int getItemCount() {
        if(videoItems != null) {
            return videoItems.size();
        }
        return 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView preview_image;
//        private VideoView video_view;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            preview_image = itemView.findViewById(R.id.preview_image);
//            video_view = itemView.findViewById(R.id.video_view);
        }
    }
}
