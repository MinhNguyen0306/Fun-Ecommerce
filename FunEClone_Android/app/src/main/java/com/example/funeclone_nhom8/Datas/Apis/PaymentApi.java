package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.CartItem;
import com.example.funeclone_nhom8.Datas.Models.Payment;
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
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PaymentApi {
    String endpoint = ConstantUtil.BASE_URL + "payment/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    PaymentApi paymentApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PaymentApi.class);

    @POST(".")
    Call<Payment> addPayment(@Body Payment payment);

    @POST("default")
    Call<String> setPaymentDefault(@Query("paymentId") Integer paymentId);

    @DELETE(".")
    Call<Void> deletePayment(@Query("paymentId") Integer paymentId);

    @GET(".")
    Call<List<Payment>> getAllPayment(@Query("userId") Integer userId);

    @GET("default")
    Call<Payment> getPaymentDefault(@Query("userId") Integer userId);

}
