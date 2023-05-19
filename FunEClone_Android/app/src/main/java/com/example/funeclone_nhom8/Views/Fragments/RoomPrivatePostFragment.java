package com.example.funeclone_nhom8.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Adapters.VideoAdapter;
import com.example.funeclone_nhom8.Adapters.VideoGridAdapter;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;

import java.util.ArrayList;
import java.util.List;

public class RoomPrivatePostFragment extends Fragment {
    public RoomPrivatePostFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_private_post, container, false);

        RecyclerView rcv_your_post = view.findViewById(R.id.rcv_your_post);


//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL, false);
//        VideoGridAdapter videoGridAdapter = new VideoGridAdapter(getContext(), getVideoData());
//        rcv_your_post.setLayoutManager(gridLayoutManager);
//        rcv_your_post.setAdapter(videoGridAdapter);

        return view;
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
//        videoItem3.setVideoDescription("HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV.");
        videoItemList.add(videoItem3);
        return videoItemList;
    }
}
