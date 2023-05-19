package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funeclone_nhom8.Datas.Apis.UserApi;
import com.example.funeclone_nhom8.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private TextView switchSignin;
    private Button btn_signup;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();

        switchSignin.setOnClickListener(view -> onClickToSignin());
        btn_signup.setOnClickListener(view -> {
            registerNewUser();
        });
    }

    private void registerNewUser() {
        if(!validateEmail() || !validatePassword() || validateConfirmPassword()) {
            return;
        }
        String email = edt_email.getText().toString();
        String password = edt_email.getText().toString();

        UserApi.userApi.registerNewUser(email, password).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.equals("success")) {
                    Toast.makeText(SignupActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SignupActivity.this, "Request Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        switchSignin = (TextView) findViewById(R.id.switch_signin);
        btn_signup = findViewById(R.id.btn_signup);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
    }

    public boolean validateEmail(){
        String v_email = edt_email.getText().toString();
        if(v_email.isEmpty()){
            edt_email.setError("Email can not be empty!");
            edt_email.requestFocus();
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(v_email).matches()){
            edt_email.setError("Please enter a valid email");
            edt_email.requestFocus();
            return false;
        }else {
            edt_email.setError(null);
            return true;
        }
    }

    public boolean validatePassword(){
        String v_pass = edt_email.getText().toString();
        if(v_pass.isEmpty()){
            edt_email.setError("Password can not be empty");
            edt_email.requestFocus();
            return false;
        }else{
            edt_email.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String confirm = edt_confirm_password.getText().toString();
        String password = edt_password.getText().toString();
        if(confirm.isEmpty()) {
            edt_confirm_password.setError("Confirm password can't empty");
            edt_confirm_password.requestFocus();
            return false;
        } else if(!confirm.equals(password)){
            edt_confirm_password.setError("Confirm password not match");
            edt_confirm_password.requestFocus();
            return false;
        } else {
            edt_confirm_password.setError(null);
            return true;
        }
    }

    private void onClickToSignin() {
        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }
}