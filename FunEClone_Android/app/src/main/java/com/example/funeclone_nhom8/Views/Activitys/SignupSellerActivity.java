package com.example.funeclone_nhom8.Views.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Adapters.CurrencyAdapter;
import com.example.funeclone_nhom8.Datas.Apis.CurrencyApi;
import com.example.funeclone_nhom8.Datas.Apis.UserApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.Address;
import com.example.funeclone_nhom8.Datas.Models.Currency;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;

import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupSellerActivity extends AppCompatActivity {
    EditText zipcode;
    EditText floor;
    EditText unit;
    EditText block_number;
    EditText road_name;
    EditText building;
    EditText state;
    EditText city;
    Button btn_signup;
    private RelativeLayout btnDateOfBirth;
    private RelativeLayout btnGender;
    private RelativeLayout btnAddress;
    private DatePicker datePickerDateOfBirth;
    private LinearLayout chooseGender;
    private LinearLayout address;
    private ImageView down_date_picker_profile;
    private ImageView down_gender_profile;
    private ImageView down_address;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText edt_holder_name;
    private EditText account_number;
    private EditText account_code;
    private EditText edt_name_edit_profile;
    private EditText edt_email;
    private Spinner spinner_bank_location;
    private Spinner spinner_bank_currency;
    private boolean isShowDatePicker = false;
    private boolean isShowChooseGender = false;
    private boolean isShowAddress = false;
    private String bankLocation="";
    private String bankCurrency="";
    private CurrencyAdapter currencyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_seller);

        initUI();

        User user = DataLocalManager.getUser();
        if(user != null) {
            Log.e("User", user.toString());
            edt_name_edit_profile.setText(user.getName());
            edt_email.setText(user.getEmail());
        }

        btnDateOfBirth.setOnClickListener(view -> {
            isShowDatePicker = !isShowDatePicker;
            if(isShowDatePicker == true){
                datePickerDateOfBirth.setVisibility(View.VISIBLE);
                down_date_picker_profile.setRotationX(180);
            }
            else {
                datePickerDateOfBirth.setVisibility(View.GONE);
                down_date_picker_profile.setRotationX(0);
            }
        });

        btnGender.setOnClickListener(view -> {
            isShowChooseGender = !isShowChooseGender;
            if(isShowChooseGender) {
                chooseGender.setVisibility(View.VISIBLE);
                down_gender_profile.setRotationX(180);
            } else {
                chooseGender.setVisibility(View.GONE);
                down_gender_profile.setRotationX(0);
            }
        });

        btnAddress.setOnClickListener(view -> {
            isShowAddress = !isShowAddress;
            if(isShowAddress) {
                address.setVisibility(View.VISIBLE);
                down_address.setRotationX(180);
                btnAddress.setBackgroundResource(R.drawable.contact_details_bottom);
            } else {
                address.setVisibility(View.GONE);
                btnAddress.setBackgroundResource(0);
                down_address.setRotationX(0);
            }
        });

        CurrencyApi.currencyApi.getAllCurrency().enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                List<Currency> currencyList = response.body();
                if(currencyList != null || !currencyList.isEmpty()) {
                    currencyAdapter = new CurrencyAdapter(SignupSellerActivity.this, R.layout.item_currency_selected, currencyList);
                    spinner_bank_currency.setAdapter(currencyAdapter);
                    spinner_bank_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            bankCurrency = currencyAdapter.getItem(i).getName();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {
            }
        });

        btn_signup.setOnClickListener(view -> {
            String holderName = edt_holder_name.getText().toString().trim();
            String accountCode = account_code.getText().toString().trim();
            int accountNumber = account_number.getText().toString().trim().equals("") ?
                    0 : Integer.parseInt(account_number.getText().toString().trim());

            User userModel = new User();
            userModel.setBankAccountHolderName(holderName);
            userModel.setBankAccountNumber(accountNumber);
            userModel.setBankIdentifierCode(accountCode);

            int zipcodeStr = zipcode.getText().toString().trim().equals("") ?
                    0 : Integer.parseInt(zipcode.getText().toString().trim());
            String floorStr = floor.getText().toString().trim();
            String unitStr = unit.getText().toString().trim();
            int block_no = block_number.getText().toString().trim().equals("") ?
                    0 : Integer.parseInt(block_number.getText().toString().trim());
            String road_nameStr = road_name.getText().toString().trim();
            String buildingSrt = building.getText().toString().trim();
            String stateStr = state.getText().toString().trim();
            String cityStr = city.getText().toString().trim();

            Address addressModel = new Address();
            addressModel.setZipCode(zipcodeStr);
            addressModel.setFloor(floorStr);
            addressModel.setBuilding(buildingSrt);
            addressModel.setBlockNumber(block_no);
            addressModel.setRoadName(road_nameStr);
            addressModel.setState(stateStr);
            addressModel.setCity(cityStr);
            addressModel.setUnit(unitStr);

            RequestBody userBody = RequestBody.create(MediaType.parse("application/json"), userModel.toJson());
            RequestBody addressBody = RequestBody.create(MediaType.parse("application/json"), addressModel.toJson());
            MultipartBody.Part multipartBodyUser = MultipartBody.Part
                            .createFormData("userDto", userModel.toJson(),userBody);
            MultipartBody.Part multipartBodyAddress = MultipartBody.Part
                            .createFormData("addressDto", addressModel.toJson(), addressBody);


            UserApi.userApi.registerSeller(multipartBodyUser, multipartBodyAddress, user.getId()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response != null) {
                        Toast.makeText(SignupSellerActivity.this, "Register Seller success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignupSellerActivity.this, "Register Seller Failed", Toast.LENGTH_LONG).show();
                    }
                    openHomeActivity();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("Signup Seller Error", t.getMessage());
                    openHomeActivity();
                }
            });
        });

    }

    private void openHomeActivity() {
        Intent intent = new Intent(SignupSellerActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }


    private void initUI() {
        btnDateOfBirth = findViewById(R.id.btn_date_of_birth);
        btnGender = findViewById(R.id.btn_gender);
        datePickerDateOfBirth = findViewById(R.id.date_picker_profile);
        chooseGender = findViewById(R.id.choose_gender);
        down_date_picker_profile = findViewById(R.id.down_date_picker_profile);
        down_gender_profile = findViewById(R.id.down_gender_profile);
        btnAddress = findViewById(R.id.btn_address);
        address = findViewById(R.id.address);
        down_address = findViewById(R.id.down_address);
        spinner_bank_location = findViewById(R.id.spinner_bank_location);
        spinner_bank_currency = findViewById(R.id.spinner_bank_currency);
        radioGroup = findViewById(R.id.radio);
        edt_holder_name = findViewById(R.id.edt_holder_name);
        account_number = findViewById(R.id.edt_account_number);
        account_code = findViewById(R.id.edt_account_code);
        zipcode = findViewById(R.id.zip_code);
        floor = findViewById(R.id.floor);
        unit = findViewById(R.id.unit);
        block_number = findViewById(R.id.block_number);
        road_name = findViewById(R.id.road_name);
        building = findViewById(R.id.building);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        btn_signup = findViewById(R.id.btn_signup);
        edt_name_edit_profile = findViewById(R.id.edt_name_edit_profile);
        edt_email = findViewById(R.id.edt_email);
    }
}