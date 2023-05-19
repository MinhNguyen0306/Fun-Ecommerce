package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Adapters.SearchResultAdapter;
import com.example.funeclone_nhom8.Datas.Apis.CartApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.CartItem;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.Datas.Models.Recommended;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView product_image;
    private TextView total_price;
    private TextView quantity;
    private ImageView btn_plus;
    private ImageView btn_minus;
    private ImageButton btn_back;
    private ImageButton btn_cart;
    private Button btn_buy_now;
    private Button btn_add_to_cart;
    private EditText edt_product_name;
    private RecyclerView rcv_also_like;
    private FlowLayout flowLayout;
    private Product product;
    private ArrayList<Recommended> reviews;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initUI();

        user = DataLocalManager.getUser();
        if(user != null) {
            btn_add_to_cart.setOnClickListener(view -> {
                Integer quantityInt = Integer.parseInt(quantity.getText().toString().trim());
                if(product != null) {
                    CartApi.cartApi.createCartItem(user.getId(), product.getId(), quantityInt).enqueue(new Callback<CartItem>() {
                        @Override
                        public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                            if(response == null) {
                                Toast.makeText(ProductDetailActivity.this, "Out of stock", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
                                builder.setTitle("Information")
                                        .setMessage("Add item to cart successful")
                                        .setPositiveButton("OK", (dialogInterface, i) -> {

                                        }).create().show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CartItem> call, Throwable t) {
                            Log.e("createCartItem Failed", t.getMessage());
                        }
                    });
                }
            });
        }

        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(this);
        searchResultAdapter.setData(getProductData());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this
                , LinearLayoutManager.HORIZONTAL, false);
        rcv_also_like.setLayoutManager(linearLayoutManager2);
        rcv_also_like.setAdapter(searchResultAdapter);

        getReviewsData();
        setDataReviews();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle != null) {
            product = (Product) bundle.getSerializable("product");
            Glide.with(this)
                    .load(ConstantUtil.BASE_URL+"product/image/"+product.getPhotos().get(0).getPhotoUrl()).into(product_image);
            edt_product_name.setText(product.getName());
            total_price.setText(String.valueOf(product.getPricing()));
        }

        btn_minus.setOnClickListener(view -> {
            Integer quantityInt = Integer.parseInt(quantity.getText().toString().trim());
            quantityInt--;
            Double total;
            if(product != null) {
                total = product.getPricing() * quantityInt;
                total_price.setText(total > 0.0 ? String.valueOf(total) : String.valueOf(product.getPricing()));
            }
            quantity.setText(quantityInt > 1 ? String.valueOf(quantityInt) : String.valueOf(1));
        });

        btn_plus.setOnClickListener(view -> {
            Integer quantityInt = Integer.parseInt(quantity.getText().toString().trim());
            quantityInt++;
            Double total;
            if(product != null) {
                total = product.getPricing() * quantityInt;
                total_price.setText(String.valueOf(total));
            }
            quantity.setText(quantityInt < product.getStock() ? String.valueOf(quantityInt) : String.valueOf(product.getStock()));
        });

        btn_buy_now.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
            startActivity(intent);
            finish();
        });

        btn_cart.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);
            finish();
        });

        btn_back.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private List<Product> getProductData() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Ba lô", 11, R.drawable.balo));
        productList.add(new Product(2, "Đồng hồ nam", 8, R.drawable.dongho));
        productList.add(new Product(3, "Giày thể thao", 15, R.drawable.giay));
        return productList;
    }

    private void getReviewsData() {
        reviews = new ArrayList<>();
        reviews.add(new Recommended(1, "Denim Jeans"));
        reviews.add(new Recommended(2, "Mini Skirt"));
        reviews.add(new Recommended(3, "Jacket"));
        reviews.add(new Recommended(4, "Accessories"));
        reviews.add(new Recommended(5, "Sports Accessories"));
        reviews.add(new Recommended(6, "Yoga Pants"));
        reviews.add(new Recommended(7, "Eye Shadow"));
    }

    private void setDataReviews() {
        if(flowLayout == null) {
            return;
        } else {
            flowLayout.removeAllViews();
            if(reviews != null && reviews.size() > 0) {
                for (Recommended recommended : reviews) {
                    TextView textView = new TextView(this);
                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(
                            FlowLayout.LayoutParams.WRAP_CONTENT,
                            FlowLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0, 0, 20, 20);
                    textView.setLayoutParams(layoutParams);
                    textView.setId(recommended.getId());
                    textView.setText(recommended.getName());
                    textView.setPadding(20, 20, 20,  20);
                    textView.setBackgroundResource(R.drawable.bg_input_text);
                    textView.setTextColor(getResources().getColor(R.color.black));

                    flowLayout.addView(textView);
                }
            }
        }
    }

    private void initUI() {
        product_image = findViewById(R.id.product_image);
        total_price = findViewById(R.id.total_price);
        quantity = findViewById(R.id.quantity);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_back = findViewById(R.id.btn_back);
        btn_buy_now = findViewById(R.id.btn_buy_now);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        edt_product_name = findViewById(R.id.edt_product_name);
        btn_cart = findViewById(R.id.btn_cart);
        rcv_also_like = findViewById(R.id.rcv_also_like);
        flowLayout = findViewById(R.id.flow_layout);
    }
}