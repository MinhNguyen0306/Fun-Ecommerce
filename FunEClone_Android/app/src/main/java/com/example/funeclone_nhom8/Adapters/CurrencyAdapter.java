package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.funeclone_nhom8.Datas.Models.Currency;
import com.example.funeclone_nhom8.R;

import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<Currency> {

    public CurrencyAdapter(@NonNull Context context, int resource, @NonNull List<Currency> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency_selected, parent, false);
        TextView tv_selected = convertView.findViewById(R.id.tv_selected);
        Currency currency = this.getItem(position);
        if(currency != null) {
            tv_selected.setText(currency.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        TextView tvCurrency = convertView.findViewById(R.id.tv_currency);
        Currency currency = this.getItem(position);
        if(currency != null) {
            tvCurrency.setText(currency.getName());
        }
        return convertView;
    }
}
