package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.Audio;
import com.example.funeclone_nhom8.Datas.Models.GroupPost;
import com.example.funeclone_nhom8.Datas.Models.GroupPostResponse;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupPostApi {
    String endpoint = ConstantUtil.BASE_URL + "group_post/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    GroupPostApi groupPostApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GroupPostApi.class);

    @Multipart
    @POST(".")
    Call<GroupPost> createGroupPost(@Query("message") String message,
                                            @Query("groupId") Integer groupId,
                                            @Part List<MultipartBody.Part> files,
                                            @Query("userId") Integer userId);

    @GET(".")
    Call<GroupPostResponse> getAllGroupPost(@Query("groupId") Integer groupId,
                                    @Query("pageNumber") Integer pageNumber,
                                    @Query("pageSize") Integer pageSize,
                                    @Query("sortBy") String sortBy,
                                    @Query("sortDir") String sortDir);

    @GET("{groupPostId}")
    Call<GroupPost> getGroupPostById(@Path("groupPostId") Integer groupPostId);

    @GET("{groupPostId}/image")
    Call<List<Photo>> getAllPhotoOfGroupPost(@Path("groupPostId") Integer groupPostId);

    @GET("{groupPostId}/video")
    Call<List<VideoItem>> getAllVideoOfGroupPost(@Path("groupPostId") Integer groupPostId);

    @GET("{groupPostId}/audio")
    Call<List<Audio>> getAllAudioOfGroupPost(@Path("groupPostId") Integer groupPostId);
}
