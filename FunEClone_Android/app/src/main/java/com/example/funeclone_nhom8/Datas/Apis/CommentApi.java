package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.Comment;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentApi {
    String endpoint = ConstantUtil.BASE_URL + "comment/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    CommentApi commentApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CommentApi.class);

    @GET(".")
    Call<List<Comment>> getAllCommentOfGroupPost(@Query("groupPostId") Integer groupPostId);

    @GET("child_comment")
    Call<List<Comment>> getAllChildComment(@Query("commentId") Integer commentId);

    @GET("{commentId}")
    Call<Comment> getCommentById(@Path("commentId") Integer commentId);

    @DELETE("{commentId}")
    Call<Comment> deleteComment(@Path("commentId") Integer commentId);

    @PUT("{commentId}")
    Call<Comment> editComment(@Path("commentId") Integer commentId);

    @POST(".")
    Call<Comment> createComment(@Query("message") String message,
                                @Query("groupPostId") Integer groupPostId,
                                @Query("userId") Integer userId);
    @POST("child_comment")
    Call<Comment> createChildComment(
        @Query("message") String message,
        @Query("groupPostId") Integer groupPostId,
        @Query("userId") Integer userId,
        @Query("parentId") Integer parentId);
}
