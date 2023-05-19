package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funeclone_nhom8.Datas.Apis.UserApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    private TextView switchSignup;
    private Button btnSignin;
    private EditText edtEmailLogin;
    private EditText edtPasswordLogin;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initUI();

        btnSignin.setOnClickListener(view -> authenticateUser());

        switchSignup.setOnClickListener(view -> onClickToSignup());
    }

    private void authenticateUser() {
        if( !validateEmail() || !validatePassword()){
            return;
        }

        String email = edtEmailLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();

        User user = new User(email, password);

        mProgressBar.setVisibility(View.VISIBLE);

        UserApi.userApi.loginUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User loginUser = response.body();
                if(loginUser != null) {
                    Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
                    DataLocalManager.setUser(loginUser);
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(SigninActivity.this, "Login Successfully!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("",t.toString());
                Toast.makeText(SigninActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.GONE);
            }
        });
        Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void initUI() {
        switchSignup = findViewById(R.id.switch_signup);
        btnSignin = findViewById(R.id.btn_signin);
        edtEmailLogin = findViewById(R.id.edt_email_login);
        edtPasswordLogin = findViewById(R.id.edt_password_login);
        mProgressBar = findViewById(R.id.progressBar);
    }

    public boolean validateEmail(){
        String v_email = edtEmailLogin.getText().toString();
        //Check if email empty
        if(v_email.isEmpty()){
            edtEmailLogin.setError("Email can not be empty!");
            edtEmailLogin.requestFocus();
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(v_email).matches()){
            edtEmailLogin.setError("Please enter a valid email");
            edtEmailLogin.requestFocus();
            return false;
        }else {
            edtEmailLogin.setError(null);
            return true;
        }
    }

    public boolean validatePassword(){
        String v_pass = edtPasswordLogin.getText().toString();
        if(v_pass.isEmpty()){
            edtPasswordLogin.setError("Password can not be empty");
            edtEmailLogin.requestFocus();
            return false;
        }else{
            edtPasswordLogin.setError(null);
            return true;
        }
    }

    public void onClickToSignup() {
        Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
}