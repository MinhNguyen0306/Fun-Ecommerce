package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.CartItem;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartApi {
    String endpoint = ConstantUtil.BASE_URL + "cart/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    CartApi cartApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CartApi.class);

    @GET(".")
    Call<List<CartItem>> getAllCartItemOfUser(@Query("userId") Integer userId);

    @POST(".")
    Call<CartItem> createCartItem(
            @Query("userId") Integer userId,
            @Query("productId") Integer productId,
            @Query("quantity") Integer quantity);

    @PATCH("{cartItemId}")
    Call<CartItem> updateQuantityCartItem(@Path("cartItemId") Integer cartItemId,
                                          @Query("quantity") Integer quantity);

    @DELETE("{cartItemId}")
    Call<String> deleteCartItem(@Path("cartItemId") Integer cartItemId);

    @GET("size")
    Call<Integer> getSizeCart(@Query("userId") Integer userId);
}
