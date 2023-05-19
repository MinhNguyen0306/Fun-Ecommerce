package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
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

public class CartActivity extends AppCompatActivity {
    private RecyclerView rcv_cart;
    private ImageButton btn_back;
    private Button proceed_to_checkout;
    private TextView txt_total_price;
    private CartAdapter cartAdapter;
    List<CartItem> cartItems;
    private double total_price = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initUI();

        cartAdapter = new CartAdapter(CartActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        rcv_cart.setLayoutManager(linearLayoutManager);
        User user = DataLocalManager.getUser();
        if(user != null) {
            CartApi.cartApi.getAllCartItemOfUser(user.getId()).enqueue(new Callback<List<CartItem>>() {
                @Override
                public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                    cartItems = response.body();
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

        if(cartItems == null || cartItems.isEmpty()) {
            proceed_to_checkout.setEnabled(false);
            proceed_to_checkout.setBackgroundResource(R.drawable.bg_disable_button);
            proceed_to_checkout.setText("No items in cart");
        }

        proceed_to_checkout.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
            finish();
        });

        btn_back.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void initUI() {
        rcv_cart = findViewById(R.id.rcv_cart);
        btn_back = findViewById(R.id.btn_back);
        proceed_to_checkout = findViewById(R.id.proceed_to_checkout);
        txt_total_price = findViewById(R.id.txt_total_price);
    }
}