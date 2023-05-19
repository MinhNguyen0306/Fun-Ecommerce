package com.example.funeclone_nhom8.Datas.Apis;

import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.Datas.Models.ProductResponse;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductApi {
    String endpoint = ConstantUtil.BASE_URL + "product/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ProductApi productApi = new Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    @GET(".")
    Call<ProductResponse> getAllProduct(@Query("pageNumber") int pageNumber,
                                        @Query("pageSize") int pageSize,
                                        @Query("sortBy") String sortBy,
                                        @Query("sortDir") String sortDir);
    @Multipart
    @POST(".")
    Call<Product> createProduct(@Part MultipartBody.Part product,
                                @Part MultipartBody.Part media,
                                @Query("categoryId") Integer categoryId,
                                @Query("currencyId") Integer currencyId);

    @GET("media/{mediaName}")
    Call<Void> getMediaResource(@Path("mediaName") String mediaName);

    @GET("search")
    Call<List<Product>> searchProductsByName(@Query("key") String key);
}
