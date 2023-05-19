package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.Audio;
import com.example.funeclone_nhom8.Datas.Models.Group;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.Datas.Models.VideoItem;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupApi {
    String endpoint = ConstantUtil.BASE_URL + "group/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    GroupApi groupApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GroupApi.class);
    
    @GET("{groupId}")
    Call<Group> getGroupById(@Path("groupId") Integer groupId);

    @GET(".")
    Call<List<Group>> getAllGroup();

    @GET("user/{userId}")
    Call<List<Group>> getAllGroupOfUser(@Path("userId") Integer userId);

    @Multipart
    @POST("user/{userId}")
    Call<Group> createGroup(
            @Path("userId") Integer userId,
            @Query("title") String title,
            @Part MultipartBody.Part avatar,
            @Part Set<MultipartBody.Part> users);

    @PATCH("{groupId}/user/{userId}")
    Call<Group> joinGroup(@Path("userId") Integer userId,
                          @Path("groupId") Integer groupId);

    @PUT("{groupId}/user/{userId")
    Call<String> leaveGroup(@Path("groupId") Integer groupId,
                            @Path("userId") Integer userId);

    @GET("{groupId}/video")
    Call<List<VideoItem>> getAllVideoOfGroup(@Path("groupId") Integer groupId);
    @GET("{groupId}/audio")
    Call<List<Audio>> getAllAudioOfGroup(@Path("groupId") Integer groupId);
    @GET("{groupId}/photo")
    Call<List<Photo>> getAllPhotoOfGroup(@Path("groupId") Integer groupId);

}
