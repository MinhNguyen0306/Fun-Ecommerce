package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Views.Activitys.ProductDetailActivity;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Product> products;
    private int TYPE_PRODUCT_ITEM_1 = 1;
    private int TYPE_PRODUCT_ITEM_2 = 2;
    private int TYPE_PRODUCT_ITEM_4 = 4;

    private final String URL_IMAGE_PRODUCT = ConstantUtil.BASE_URL + "product/image/";

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(TYPE_PRODUCT_ITEM_1 == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_1, parent, false);
            return new ProductViewHolder1(view);
        } else if(TYPE_PRODUCT_ITEM_2 == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_2, parent, false);
            return new ProductViewHolder2(view);
        } else if(TYPE_PRODUCT_ITEM_4 == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_4, parent, false);
            return new ProductViewHolder4(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);
        if(product == null) {
            return;
        }
        Optional<List<Photo>> photos = Optional.ofNullable(product.getPhotos());
        if(photos.isPresent()) {
            List<String> imageUrlList = photos.get().stream().map(photo -> photo.getPhotoUrl()).collect(Collectors.toList());
            if(imageUrlList.isEmpty() || imageUrlList.size()==0) {
                return;
            } else {
                if(TYPE_PRODUCT_ITEM_1 == holder.getItemViewType()) {
                    ProductViewHolder1 productViewHolder1 = (ProductViewHolder1) holder;
                    imageUrlList.stream().limit(1).forEach(image -> {
                        Glide.with(context).load(URL_IMAGE_PRODUCT+image).into(productViewHolder1.image_product_1);
                    });
                    productViewHolder1.txt_product_stock.setText(String.valueOf(product.getStock()));
                    productViewHolder1.product_name.setText(product.getName());
                    productViewHolder1.item_product.setOnClickListener(view -> {
                        openProductDetailActivity(product);
                    });
                } else if(TYPE_PRODUCT_ITEM_2 == holder.getItemViewType()) {
                    ProductViewHolder2 productViewHolder2 = (ProductViewHolder2) holder;
                    List<ImageView> imageViews = Arrays.asList(productViewHolder2.image_product_1, productViewHolder2.image_product_2);
                    for(int i = 0; i < imageViews.size(); i++) {
                        Glide.with(context).load(URL_IMAGE_PRODUCT+imageUrlList.get(i)).into(imageViews.get(i));
                    }
                    productViewHolder2.txt_product_stock.setText(String.valueOf(product.getStock()));
                    productViewHolder2.product_name.setText(product.getName());
                    productViewHolder2.item_product.setOnClickListener(view -> {
                        openProductDetailActivity(product);
                    });
                } else if(TYPE_PRODUCT_ITEM_4 == holder.getItemViewType()){
                    ProductViewHolder4 productViewHolder4 = (ProductViewHolder4) holder;
                    List<ImageView> imageViews = Arrays.asList(productViewHolder4.image_product_1,
                            productViewHolder4.image_product_2, productViewHolder4.image_product_3, productViewHolder4.image_product_4);
                    for(int i = 0; i < imageViews.size(); i++) {
                        Glide.with(context).load(URL_IMAGE_PRODUCT+imageUrlList.get(i)).into(imageViews.get(i));
                    }
                    productViewHolder4.txt_product_stock.setText(String.valueOf(product.getStock()));
                    productViewHolder4.product_name.setText(product.getName());
                    productViewHolder4.item_product.setOnClickListener(view -> {
                        openProductDetailActivity(product);
                    });
                }
            }
        }
    }

    private void openProductDetailActivity(Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(products != null) {
            return products.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Product product = products.get(position);
        if(product.getTypeDisplay() == 1) {
            return TYPE_PRODUCT_ITEM_1;
        } else if(product.getTypeDisplay() == 2) {
            return TYPE_PRODUCT_ITEM_2;
        } else {
            return TYPE_PRODUCT_ITEM_4;
        }
    }

    public class ProductViewHolder1 extends RecyclerView.ViewHolder {
        private ImageView image_product_1;
        private TextView txt_product_stock;
        private TextView product_name;
        private LinearLayout item_product;
        public ProductViewHolder1(@NonNull View itemView) {
            super(itemView);
            image_product_1 = itemView.findViewById(R.id.image_product_100);
            txt_product_stock = itemView.findViewById(R.id.txt_product_stock);
            item_product = itemView.findViewById(R.id.item_product);
            product_name = itemView.findViewById(R.id.product_name);
        }
    }

    public class ProductViewHolder2 extends RecyclerView.ViewHolder {
        private TextView txt_product_stock;
        private ImageView image_product_1;
        private ImageView image_product_2;
        private TextView product_name;

        private LinearLayout item_product;
        public ProductViewHolder2(@NonNull View itemView) {
            super(itemView);
            txt_product_stock = itemView.findViewById(R.id.txt_product_stock);
            image_product_1 = itemView.findViewById(R.id.image_product_1);
            image_product_2 = itemView.findViewById(R.id.image_product_2);
            item_product = itemView.findViewById(R.id.item_product);
            product_name = itemView.findViewById(R.id.product_name);

        }
    }

    public class ProductViewHolder4 extends RecyclerView.ViewHolder {
        private ImageView image_product_1;
        private ImageView image_product_2;
        private ImageView image_product_3;
        private ImageView image_product_4;
        private TextView txt_product_stock;
        private TextView product_name;

        private LinearLayout item_product;
        public ProductViewHolder4(@NonNull View itemView) {
            super(itemView);
            txt_product_stock = itemView.findViewById(R.id.txt_product_stock);
            image_product_1 = itemView.findViewById(R.id.image_product_1);
            image_product_2 = itemView.findViewById(R.id.image_product_2);
            image_product_3 = itemView.findViewById(R.id.image_product_3);
            image_product_4 = itemView.findViewById(R.id.image_product_4);
            item_product = itemView.findViewById(R.id.item_product);
            product_name = itemView.findViewById(R.id.product_name);

        }
    }
}
