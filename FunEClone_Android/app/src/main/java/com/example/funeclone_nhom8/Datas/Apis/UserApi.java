package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    String endpoint = ConstantUtil.BASE_URL + "user/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    UserApi userApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserApi.class);

    @POST("login")
    Call<User> loginUser(@Body User user);

    @Multipart
    @PUT("update/{userID}")
    Call<User> updateUser(@Part("name") RequestBody name,
                          @Part MultipartBody.Part avatar,
                          @Part MultipartBody.Part coverAvatar,
                          @Path("userID") Integer userID);

    @GET("image/{imageName}")
    Call<Void> getImageResource(@Path("imageName") String imageName);

    @POST("register")
    Call<String> registerNewUser(@Query("email") String email, @Query("password") String password);

    @GET("search")
    Call<List<User>> searchUsers(@Query("key") String key);

    @Multipart
    @POST("seller/register/{userId}")
    Call<User> registerSeller(@Part MultipartBody.Part user,
                              @Part MultipartBody.Part address,
                              @Path("userId") Integer userId);
}
