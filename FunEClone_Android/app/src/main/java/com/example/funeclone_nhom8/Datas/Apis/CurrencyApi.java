package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.Currency;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApi {
    String endpoint = ConstantUtil.BASE_URL + "currency/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    CurrencyApi currencyApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CurrencyApi.class);

    @GET("{currencyId}")
    Call<Currency> getCurrencyById(@Path(value = "currencyId") Integer currencyId);

    @GET(".")
    Call<List<Currency>> getAllCurrency();
}
