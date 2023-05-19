package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Utils.IClickOpenCommentListener;
import com.example.funeclone_nhom8.Utils.IOnclickBackFromReel;
import com.example.funeclone_nhom8.Views.Activitys.VideoPostActivity;
import com.example.funeclone_nhom8.Views.Fragments.RoomFragment;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReelAdapter extends RecyclerView.Adapter<ReelAdapter.VideoViewHolder>{
    private Context context;
    private List<VideoItem> videoItemList;
    private IClickOpenCommentListener iClickOpenCommentListener;
    private IOnclickBackFromReel iOnclickBackFromReel;

    public ReelAdapter(Context context, List<VideoItem> videoItemList, IClickOpenCommentListener iClickOpenCommentListener, IOnclickBackFromReel iOnclickBackFromReel) {
        this.context = context;
        this.videoItemList = videoItemList;
        this.iClickOpenCommentListener = iClickOpenCommentListener;
        this.iOnclickBackFromReel = iOnclickBackFromReel;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_video_post, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem videoItem = videoItemList.get(position);
        holder.setVideoData(videoItem);
        holder.btn_open_comment.setOnClickListener(view -> {
            iClickOpenCommentListener.onClickOpenComment(videoItem);
        });

        holder.btn_back.setOnClickListener(view -> {
            iOnclickBackFromReel.onClickBackFromReel();
        });
    }

    @Override
    public int getItemCount() {
        if(videoItemList != null) {
            return videoItemList.size();
        }
        return 0;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView textVideoTitle, textVideoDescription;
        ProgressBar videoProgressbar;
        ImageView play;
        ImageView btn_open_comment;
        ImageView btn_back;
        MediaPlayer mediaPlayer1;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
            textVideoTitle = itemView.findViewById(R.id.text_video_title);
            textVideoDescription = itemView.findViewById(R.id.text_video_description);
            videoProgressbar = itemView.findViewById(R.id.video_progressbar);
            play = itemView.findViewById(R.id.play);
            btn_back = itemView.findViewById(R.id.btn_back);
            btn_open_comment = itemView.findViewById(R.id.btn_open_comment);
        }

        void setVideoData(VideoItem videoItem) {
            textVideoTitle.setText(videoItem.getVideoTitle());
//            textVideoDescription.setText(videoItem.getVideoDescription());
            String path = ConstantUtil.BASE_URL+"group_post/video/"+videoItem.getVideoUrl();
            Log.e("Path", path);
            videoView.setVideoPath(path);
            videoView.setOnPreparedListener(mediaPlayer -> {
                videoProgressbar.setVisibility(View.GONE);
                mediaPlayer1 = mediaPlayer;
                mediaPlayer.start();
                float videoRatio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
                float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                float scale = videoRatio / screenRatio;
                if(scale > 1f) {
                    videoView.setScaleX(scale);
                } else {
                    videoView.setScaleY(1f / scale);
                }
            });
            videoView.setOnCompletionListener(mediaPlayer -> {
                mediaPlayer.start();
            });

            videoView.setOnClickListener(view -> {
                if(mediaPlayer1.isPlaying()) {
                    mediaPlayer1.pause();
                    play.setVisibility(View.VISIBLE);
                } else {
                    mediaPlayer1.start();
                    play.setVisibility(View.GONE);
                }
            });
        }
    }
}
