package com.example.funeclone_nhom8.Views.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.funeclone_nhom8.Adapters.CategoryAdapter;
import com.example.funeclone_nhom8.Adapters.CurrencyAdapter;
import com.example.funeclone_nhom8.Datas.Apis.CategoryApi;
import com.example.funeclone_nhom8.Datas.Apis.CurrencyApi;
import com.example.funeclone_nhom8.Datas.Apis.ProductApi;
import com.example.funeclone_nhom8.Datas.Models.Category;
import com.example.funeclone_nhom8.Datas.Models.Currency;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.RealPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadProductFragment extends Fragment {
    private static final int MY_REQUEST_CODE = 10;
    private static final int MY_REQUEST_CODE_CAMERA = 100;
    private Uri mUriData;
    private Spinner dropdown_category;
    private CategoryAdapter categoryAdapter;
    private Spinner dropdown_currency;
    private CurrencyAdapter currencyAdapter;
    private Button btn_open_gallery;
    private Button btn_open_camera;
    private ImageView image_product;
    private EditText edt_product_name;
    private EditText edt_product_description;
    private EditText edt_pricing;
    private EditText edt_stock;
    private Button btn_cancel;
    private Button btn_next;
    private int categoryId;
    private int currencyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_product, container, false);

        initUI(view);

        CategoryApi.categoryApi.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                if(response.body() != null || !response.body().isEmpty()) {
                    categoryAdapter = new CategoryAdapter(getContext(), R.layout.item_category_selected, categoryList);
                    dropdown_category.setAdapter(categoryAdapter);
                    dropdown_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            categoryId = categoryAdapter.getItem(i).getId();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });

        CurrencyApi.currencyApi.getAllCurrency().enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                List<Currency> currencyList = response.body();
                if(currencyList != null || !currencyList.isEmpty()) {
                    currencyAdapter = new CurrencyAdapter(getContext(), R.layout.item_currency_selected, currencyList);
                    dropdown_currency.setAdapter(currencyAdapter);
                    dropdown_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            currencyId = currencyAdapter.getItem(i).getId();
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

        btn_next.setOnClickListener(view13 -> {
            String name = edt_product_name.getText().toString().trim();
            String description = edt_product_description.getText().toString().trim();
            double pricing = Double.parseDouble(edt_pricing.getText().toString().trim());
            int stock = Integer.parseInt(edt_stock.getText().toString().trim());

            Product product = new Product(name, description, pricing, stock);

            String realPathImage = RealPathUtil.getRealPath(this.getContext(), mUriData);
            File fileProductImage = new File(realPathImage);

            RequestBody requestBodyProduct = RequestBody.create(MediaType.parse("application/json"), product.toJson());
            MultipartBody.Part multipartBodyProduct = MultipartBody.Part
                    .createFormData("product",product.toJson(), requestBodyProduct);

            RequestBody requestBodyImage = RequestBody.create(MediaType.parse("multipart/form-data"), fileProductImage);
            MultipartBody.Part multipartBodyImage = MultipartBody.Part.
                    createFormData("media", fileProductImage.getName(), requestBodyImage);

            ProductApi.productApi.createProduct(multipartBodyProduct, multipartBodyImage, categoryId, currencyId).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.code() == 201) {
                        openHomeFragment();
                        Toast.makeText(getContext(), "Product added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Product not added", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                }
            });
        });

        btn_open_gallery.setOnClickListener(view1 -> {
            onClickRequestPermission();
        });

        btn_open_camera.setOnClickListener(view12 -> {
            onClickRequestPermissionCamera();
        });

        return view;
    }

    /********** OPEN CAMERA HANDLE ********/
    private void onClickRequestPermissionCamera() {
        if(getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            onClickOpenCamera();
        } else {
            String[] permission = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(getActivity(),permission, MY_REQUEST_CODE_CAMERA);
        }
    }
    private void onClickOpenCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncherCamera.launch(Intent.createChooser(intent, "Capture Image"));
    }
    private ActivityResultLauncher<Intent> activityResultLauncherCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null) {
                            return;
                        }
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        image_product.setImageBitmap(bitmap);
                        image_product.setMaxHeight(200);

                        mUriData = getImageUri(getContext().getApplicationContext(), bitmap);
                    }
                }
            }
    );
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Bitmap outImage = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), outImage, "Title", null);
        return Uri.parse(path);
    }
    /**********   END OPEN CAMERA HANDLE   **********/



    /**********   OPEN GALLERY HANDLE   **********/
    private void onClickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onclickOpenGallery();
            return;
        }
        if(getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            onclickOpenGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(getActivity(),permission, MY_REQUEST_CODE);
        }
    }
    private void onclickOpenGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if(data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUriData = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            image_product.setImageBitmap(bitmap);
                            image_product.setMaxHeight(200);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onclickOpenGallery();
            }
        } else if(requestCode == MY_REQUEST_CODE_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onClickOpenCamera();
            }
        }
    }
    /**********   END OPEN GALLERY HANDLE   **********/


    private void openHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_nav, homeFragment).commit();
    }

    public void initUI(View view) {
        dropdown_category = view.findViewById(R.id.dropdown_category);
        dropdown_currency = view.findViewById(R.id.dropdown_currency);
        btn_open_gallery = view.findViewById(R.id.btn_open_gallery);
        btn_open_camera = view.findViewById(R.id.btn_open_camera);
        image_product = view.findViewById(R.id.image_product);
        edt_product_name = view.findViewById(R.id.edt_product_name);
        edt_product_description = view.findViewById(R.id.edt_product_description);
        edt_pricing = view.findViewById(R.id.edt_pricing);
        edt_stock = view.findViewById(R.id.edt_stock);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_next = view.findViewById(R.id.btn_next);
    }
}
