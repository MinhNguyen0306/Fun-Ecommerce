package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Models.Photo;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Optional;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    Context context;
    List<Product> products;
    private final String URL_IMAGE_PRODUCT = ConstantUtil.BASE_URL + "product/image/";

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        Product product = products.get(position);
        holder.txt_name.setText(product.getName());
        if(product.getCurrency() != null) {
            holder.txt_price.setText(product.getPricing() + " " + product.getCurrency().getName());
        }

        if(product.getImageResource() != 0) {
            holder.imageView.setImageResource(product.getImageResource());
        }

        Optional<List<Photo>> photosOptional = Optional.ofNullable(product.getPhotos());
        if(photosOptional.isPresent()){
            List<Photo> photos = photosOptional.get();
            if(!photos.isEmpty()) {
                Glide.with(context).load(URL_IMAGE_PRODUCT+photos.get(0).getPhotoUrl()).into(holder.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(products != null) {
            return products.size();
        }
        return 0;
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txt_name;
        private TextView txt_price;
        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
        }
    }
}
