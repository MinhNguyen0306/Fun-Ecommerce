package com.example.funeclone_nhom8.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Adapters.ProductAdapter;
import com.example.funeclone_nhom8.Adapters.SearchResultAdapter;
import com.example.funeclone_nhom8.Datas.Apis.ProductApi;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultFragment extends Fragment {

    private RecyclerView rcv_search_result;
    private EditText edt_search;
    private LinearLayout key_search;
    private TextView btn_close_key_search;
    private TextView txt_search_key;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        initUI(view);

        Bundle arguments = getArguments();
        String searchKey = arguments.getString("key");

        txt_search_key.setText(searchKey);
        btn_close_key_search.setOnClickListener(view1 -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.content_nav, new SearchFragment());
            fragmentTransaction.commit();
        });

        ProductApi.productApi.searchProductsByName(searchKey).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response != null || response.body()!= null) {
                    List<Product> productList = response.body();
                    SearchResultAdapter searchResultAdapter = new SearchResultAdapter(getContext());
                    searchResultAdapter.setData(productList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    rcv_search_result.setLayoutManager(gridLayoutManager);
                    rcv_search_result.setAdapter(searchResultAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });

        return view;
    }

    private void initUI(View view) {
        rcv_search_result = view.findViewById(R.id.rcv_search_result);
        edt_search = view.findViewById(R.id.edt_search);
        key_search = view.findViewById(R.id.key_search);
        txt_search_key = view.findViewById(R.id.txt_search_key);
        btn_close_key_search = view.findViewById(R.id.btn_close_key_search);
    }
}
