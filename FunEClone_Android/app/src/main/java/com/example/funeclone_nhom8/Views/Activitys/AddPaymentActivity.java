package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.funeclone_nhom8.R;

public class AddPaymentActivity extends AppCompatActivity {

    private EditText edt_card_number;
    private EditText edt_expires_on;
    String a;
    int keyDel;
    String b;
    int keyDel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        initUI();

        edt_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean flag = true;
                String eachBlock[] = edt_card_number.getText().toString().split(" ");
                for (int j = 0; j < eachBlock.length; j++) {
                    if (eachBlock[j].length() > 4) {
                        flag = false;
                    }
                }
                if (flag) {
                    edt_card_number.setOnKeyListener((v, keyCode, event) -> {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    });

                    if (keyDel == 0) {
                        if (((edt_card_number.getText().length() + 1) % 5) == 0) {
                            if (edt_card_number.getText().toString().split(" ").length <= 3) {
                                edt_card_number.setText(edt_card_number.getText() + " ");
                                edt_card_number.setSelection(edt_card_number.getText().length());
                            }
                        }
                        a = edt_card_number.getText().toString();
                    } else {
                        a = edt_card_number.getText().toString();
                        keyDel = 0;
                    }
                } else {
                    edt_card_number.setText(a);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edt_expires_on.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean flag = true;
                String eachBlock[] = edt_expires_on.getText().toString().split("/");
                for (int j = 0; j < eachBlock.length; j++) {
                    if (eachBlock[j].length() > 2  && !edt_expires_on.getText().toString().contains("/")) {
                        flag = false;
                    }
                }
                if (flag) {
                    edt_expires_on.setOnKeyListener((v, keyCode, event) -> {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel2 = 1;
                        return false;
                    });

                    if (keyDel2 == 0) {
                        if (((edt_expires_on.getText().length() + 1) % 3) == 0) {
                            if (edt_expires_on.getText().toString().split("/").length <= 2
                                    && !edt_expires_on.getText().toString().contains("/")) {
                                edt_expires_on.setText(edt_expires_on.getText() + "/");
                                edt_expires_on.setSelection(edt_expires_on.getText().length());
                            }
                        }
                        b = edt_expires_on.getText().toString();
                    } else {
                        b = edt_expires_on.getText().toString();
                        keyDel2 = 0;
                    }
                } else {
                    edt_expires_on.setText(b);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initUI() {
        edt_card_number = findViewById(R.id.edt_card_number);
        edt_expires_on = findViewById(R.id.edt_expires_on);
    }
}