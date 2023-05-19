package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.funeclone_nhom8.Adapters.CartAdapter;
import com.example.funeclone_nhom8.Datas.Apis.CartApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.CartItem;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private RecyclerView rcv_cart;
    private TextView txt_total_price;
    private double total_price = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initUI();

        CartAdapter cartAdapter = new CartAdapter(CheckoutActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckoutActivity.this, LinearLayoutManager.VERTICAL, false);
        rcv_cart.setLayoutManager(linearLayoutManager);
        User user = DataLocalManager.getUser();
        if(user != null) {
            CartApi.cartApi.getAllCartItemOfUser(user.getId()).enqueue(new Callback<List<CartItem>>() {
                @Override
                public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                    List<CartItem> cartItems = response.body();
                    if(cartItems != null) {
                        cartAdapter.setData(cartItems);
                        rcv_cart.setAdapter(cartAdapter);
                        for(CartItem cartItem : cartItems) {
                            double cart_item_price = cartItem.getQuantity() * cartItem.getProduct().getPricing();
                            total_price += cart_item_price;
                        }
                        txt_total_price.setText(String.valueOf(total_price));
                    }
                }
                @Override
                public void onFailure(Call<List<CartItem>> call, Throwable t) {
                    Log.e("Cart Api Failed", t.getMessage());
                }
            });
        }
    }

    private void initUI() {
        rcv_cart = findViewById(R.id.rcv_cart);
      txt_total_price = findViewById(R.id.txt_total_price);
    }
}