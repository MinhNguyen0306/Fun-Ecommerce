package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Datas.Models.Payment;
import com.example.funeclone_nhom8.R;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentMethodViewHolder> {

    private Context context;
    private List<Payment> payments;

    public PaymentMethodAdapter(Context context) {
        this.context = context;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @NonNull
    @Override
    public PaymentMethodAdapter.PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent);
        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodAdapter.PaymentMethodViewHolder holder, int position) {
        Payment payment = payments.get(position);
        holder.edt_card_number.setText(payment.getCreditCardNumber());
        holder.btn_delete.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        if(payments != null) {
            return payments.size();
        }
        return 0;
    }

    public class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
        private EditText edt_card_number;
        private ImageButton btn_delete;

        public PaymentMethodViewHolder(@NonNull View itemView) {
            super(itemView);
            edt_card_number = itemView.findViewById(R.id.edt_card_number);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
