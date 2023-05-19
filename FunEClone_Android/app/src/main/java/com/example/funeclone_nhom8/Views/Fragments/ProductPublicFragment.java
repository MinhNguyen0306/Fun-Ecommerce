package com.example.funeclone_nhom8.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Adapters.ProductAdapter;
import com.example.funeclone_nhom8.Datas.Apis.ProductApi;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.Datas.Models.ProductResponse;
import com.example.funeclone_nhom8.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPublicFragment extends Fragment {

    private RecyclerView rcv_product;
    private ProductAdapter productAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_public, container, false);

        initUI(view);

        productAdapter = new ProductAdapter(this.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        rcv_product.setLayoutManager(linearLayoutManager);

        ProductApi.productApi.getAllProduct(0,4,null,null).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                Log.e("Products", response.body().toString());
                if(response.isSuccessful()) {
                    List<Product> products = response.body().getContent();
                    productAdapter.setData(products);
                    rcv_product.setAdapter(productAdapter);
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
            }
        });

        return view;
    }

    private void initUI(View view) {
        rcv_product = view.findViewById(R.id.rcv_product);
    }
}
