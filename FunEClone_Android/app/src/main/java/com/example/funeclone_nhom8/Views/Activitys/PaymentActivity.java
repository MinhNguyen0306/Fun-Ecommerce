package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.funeclone_nhom8.R;

public class PaymentActivity extends AppCompatActivity {

    private Button btn_add_another_method;
    private RecyclerView rcv_payment_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn_add_another_method = findViewById(R.id.btn_add_another_method);
        rcv_payment_method = findViewById(R.id.rcv_payment_method);

        btn_add_another_method.setOnClickListener(view -> {
            Intent intent = new Intent(PaymentActivity.this, AddPaymentActivity.class);
            startActivity(intent);
            finish();
        });


    }
}