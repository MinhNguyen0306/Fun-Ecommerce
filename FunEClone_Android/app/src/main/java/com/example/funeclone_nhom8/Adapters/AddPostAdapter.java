package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.R;

import java.util.List;

public class AddPostAdapter extends RecyclerView.Adapter<AddPostAdapter.AddPostViewHolder> {

    private List<Bitmap> imageList;

    public AddPostAdapter(List<Bitmap> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public AddPostAdapter.AddPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_live_data, parent, false);
        return new AddPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddPostAdapter.AddPostViewHolder holder, int position) {
        Bitmap image = imageList.get(position);
        holder.imageButton.setImageBitmap(image);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        if((position + 1) % 3 == 0) {
            layoutParams.setMargins(0, 0, 0,10);
        } else {
            layoutParams.setMargins(0, 0, 10,10);
        }
        holder.item_post_live_data.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        if(imageList != null) {
            return imageList.size();
        }
        return 0;
    }

    public class AddPostViewHolder extends RecyclerView.ViewHolder {
        private ImageButton imageButton;
        private CardView item_post_live_data;
        public AddPostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.livedata);
            item_post_live_data = itemView.findViewById(R.id.item_post_live_data);
        }
    }
}
