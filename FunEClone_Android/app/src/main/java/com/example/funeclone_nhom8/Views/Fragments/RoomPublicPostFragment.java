package com.example.funeclone_nhom8.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.example.funeclone_nhom8.Datas.Apis.GroupApi;
import com.example.funeclone_nhom8.Datas.Apis.GroupPostApi;
import com.example.funeclone_nhom8.Datas.Models.Audio;
import com.example.funeclone_nhom8.Datas.Models.GroupPost;
import com.example.funeclone_nhom8.Datas.Models.GroupPostResponse;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomPublicPostFragment extends Fragment {

    private int groupId;
    public RoomPublicPostFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_public_post, container, false);

        RecyclerView rcv_your_post = view.findViewById(R.id.rcv_your_post);
        RecyclerView rcv_post = view.findViewById(R.id.rcv_post);

        Bundle bundle = getArguments();
        if(bundle != null) {
            groupId = bundle.getInt("groupId");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        VideoAdapter videoAdapter = new VideoAdapter(getContext(), getVideoData());
        rcv_your_post.setLayoutManager(linearLayoutManager);
        rcv_your_post.setAdapter(videoAdapter);



//        GroupPostApi.groupPostApi.getAllGroupPost(1,0,4,null,null).enqueue(new Callback<GroupPostResponse>() {
//            @Override
//            public void onResponse(Call<GroupPostResponse> call, Response<GroupPostResponse> response) {
//                List<GroupPost> groupPostList = response.body().getContent();
//                List collectionMedia = new ArrayList();
//                for (GroupPost groupPost : groupPostList) {
//                    if(!groupPost.getVideos().isEmpty() && groupPost.getVideos() != null) {
//                        for(VideoItem videoItem : groupPost.getVideos()) {
//                            if(videoItem.getGroupPost() != null)
//                                collectionMedia.add(videoItem);
//                        }
//                    }
//                    if(!groupPost.getPhotos().isEmpty() && groupPost.getPhotos() != null) {
//                        for(Photo photo : groupPost.getPhotos()) {
//                            if(photo.getGroupPost() != null)
//                                collectionMedia.add(photo);
//                        }
//                    }
//                    if(!groupPost.getAudioSet().isEmpty() && groupPost.getAudioSet() != null) {
//                        for (Audio audio : groupPost.getAudioSet()) {
//                            if(audio.getGroupPost() != null)
//                                collectionMedia.add(audio);
//                        }
//                    }
//                }
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
//                VideoGridAdapter videoGridAdapter = new VideoGridAdapter(getContext(), collectionMedia);
//                rcv_post.setLayoutManager(gridLayoutManager);
//                rcv_post.setAdapter(videoGridAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<GroupPostResponse> call, Throwable t) {
//                Log.e("Load Group Post Error", t.getMessage());
//            }
//        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        VideoGridAdapter videoGridAdapter = new VideoGridAdapter(getContext());
        rcv_post.setLayoutManager(gridLayoutManager);
        rcv_post.setAdapter(videoGridAdapter);

        GroupApi.groupApi.getAllVideoOfGroup(groupId).enqueue(new Callback<List<VideoItem>>() {
            @Override
            public void onResponse(Call<List<VideoItem>> call, Response<List<VideoItem>> response) {
                if(response != null && !response.body().isEmpty())
                    videoGridAdapter.setMediaList(response.body());
            }

            @Override
            public void onFailure(Call<List<VideoItem>> call, Throwable t) {
            }
        });

        GroupApi.groupApi.getAllAudioOfGroup(groupId).enqueue(new Callback<List<Audio>>() {
            @Override
            public void onResponse(Call<List<Audio>> call, Response<List<Audio>> response) {
                if(response != null && !response.body().isEmpty())
                    videoGridAdapter.setMediaList(response.body());
            }

            @Override
            public void onFailure(Call<List<Audio>> call, Throwable t) {

            }
        });

        GroupApi.groupApi.getAllPhotoOfGroup(groupId).enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if(response != null && !response.body().isEmpty())
                    videoGridAdapter.setMediaList(response.body());
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });

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
//        videoItem3.setVideoDescription("HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV");
        videoItemList.add(videoItem3);
        return videoItemList;
    }

    private List<VideoItem> getAllPosting() {
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

        VideoItem videoItem4 = new VideoItem();
        videoItem4.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4");
        videoItem4.setVideoTitle("Volkswagen GTI Review");
//        videoItem4.setVideoDescription("The Smoking Tire heads out to Adams Motorsports Park in Riverside");
        videoItemList.add(videoItem4);

        VideoItem videoItem5 = new VideoItem();
        videoItem5.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4");
        videoItem5.setVideoTitle("What care can you get for a grand?");
//        videoItem5.setVideoDescription("The Smoking Tire meets up with Chris and Jorge from CarsForAGrand.com to see");
        videoItemList.add(videoItem5);

        VideoItem videoItem6 = new VideoItem();
        videoItem6.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4");
        videoItem6.setVideoTitle("We Are Going On Bullrun");
//        videoItem6.setVideoDescription("The Smoking Tire heads out to Adams Motorsports Park in Riverside");
        videoItemList.add(videoItem6);
        return videoItemList;
    }
}
