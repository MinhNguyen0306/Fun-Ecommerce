package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Apis.CartApi;
import com.example.funeclone_nhom8.Datas.Models.CartItem;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    public List<CartItem> getData() {
        return this.cartItems;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.txt_name.setText(cartItem.getProduct().getName());
        holder.txt_description.setText(cartItem.getProduct().getDescription());
        holder.txt_price.setText(String.valueOf(cartItem.getProduct().getPricing()));
        holder.txt_quantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.txt_total_price.setText(String.valueOf(cartItem.getQuantity() * cartItem.getProduct().getPricing()));
        Glide.with(context).load(ConstantUtil.BASE_URL+"product/image/"+cartItem.getProduct().getPhotos().get(0).getPhotoUrl())
                .into(holder.avatar);
        holder.btn_plus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.txt_quantity.getText().toString().trim()) + 1;
            onClickPlusQuantity(cartItem.getId(), quantity);
        });
        holder.btn_minus.setOnClickListener(view -> {
            int quantity = Integer.parseInt(holder.txt_quantity.getText().toString().trim()) - 1;
            onClickPlusQuantity(cartItem.getId(), quantity);
        });
    }

    private void onClickPlusQuantity(Integer cartItemId, Integer quantity) {
        CartApi.cartApi.updateQuantityCartItem(cartItemId, quantity).enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {
                Log.e("Cart quantity update failed", t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cartItems != null) {
            return cartItems.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name;
        private TextView txt_description;
        private TextView txt_price;
        private TextView txt_quantity;
        private TextView txt_total_price;
        private ImageView btn_plus;
        private ImageView btn_minus;
        private ImageView avatar;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_quantity = itemView.findViewById(R.id.txt_quantity);
            txt_total_price = itemView.findViewById(R.id.txt_total_price);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
