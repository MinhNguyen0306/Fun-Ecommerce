package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.funeclone_nhom8.Datas.Models.Category;
import com.example.funeclone_nhom8.R;

import java.util.List;
import java.util.Optional;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_selected, parent, false);
        TextView tv_selected = convertView.findViewById(R.id.tv_selected);
        Optional<Category> categoryOptional = Optional.ofNullable(this.getItem(position));
        if(categoryOptional.isPresent()) {
            tv_selected.setText(categoryOptional.get().getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        TextView tvCategory = convertView.findViewById(R.id.tv_category);
        Optional<Category> categoryOptional = Optional.ofNullable(this.getItem(position));
        if(categoryOptional.isPresent()) {
            tvCategory.setText(categoryOptional.get().getName());
        }
        return convertView;
    }
}
