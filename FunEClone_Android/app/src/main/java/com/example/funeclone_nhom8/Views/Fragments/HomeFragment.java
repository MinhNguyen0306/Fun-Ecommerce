package com.example.funeclone_nhom8.Views.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Adapters.ProductAdapter;
import com.example.funeclone_nhom8.Adapters.TabLayoutHomeAdapter;
import com.example.funeclone_nhom8.Datas.Apis.ProductApi;
import com.example.funeclone_nhom8.Datas.Apis.UserApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.Product;
import com.example.funeclone_nhom8.Datas.Models.ProductResponse;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Views.Activitys.CartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    FloatingActionButton btnOpenHomeDialog;
    private CircleImageView avatar;
    private ImageView cover_avatar;
    private ImageView btn_open_cart;
    private TextView cart_count;
    private TabLayout tab_layout;
    private ViewPager2 viewPager2;


    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI(view);

        Integer cartCount = DataLocalManager.getCountCart();
        if(cartCount > 0) {
            cart_count.setVisibility(View.VISIBLE);
            cart_count.setText(String.valueOf(cartCount));
        } else {
            cart_count.setVisibility(View.GONE);
        }

        user = DataLocalManager.getUser();
        if(user != null) {
            String urlAvatar = "http://"+ ConstantUtil.IPV4+":9090/api/v1/user/image/" + user.getAvatar();
            String urlCoverAvatar = "http://"+ConstantUtil.IPV4+":9090/api/v1/user/image/" + user.getCoverAvatar();

            Glide.with(this.getActivity()).load(urlAvatar).into(avatar);
            Glide.with(this.getActivity()).load(urlCoverAvatar).into(cover_avatar);
        }

        TabLayoutHomeAdapter tabLayoutHomeAdapter = new TabLayoutHomeAdapter(getActivity());
        viewPager2.setAdapter(tabLayoutHomeAdapter);
        new TabLayoutMediator(tab_layout, viewPager2, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.grid);
                    break;
                case 1:
                    tab.setIcon(R.drawable.mine);
                    break;
            }
        })).attach();

        btnOpenHomeDialog.setOnClickListener(view1 -> openHomeActionDialog(Gravity.CENTER));

        btn_open_cart.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), CartActivity.class);
            startActivity(intent);
            getActivity().finish();
        });


        return view;
    }

    private void openHomeActionDialog(int gravity) {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_home);

        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        RelativeLayout openAddProduct = dialog.findViewById(R.id.open_add_product);
        ImageButton closeDialog = dialog.findViewById(R.id.btn_close_home_action_dialog);

        closeDialog.setOnClickListener(view -> dialog.dismiss());

        openAddProduct.setOnClickListener(view -> {
            openUploadProductFragment();
            dialog.dismiss();
        });

        dialog.show();
    }

    // OPEN UPLOAD PRODUCT ACTIVITY
    private void openUploadProductFragment() {
        UploadProductFragment uploadProductFragment = new UploadProductFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_nav, uploadProductFragment).commit();
    }

    private void initUI(View view) {
        btnOpenHomeDialog = view.findViewById(R.id.btn_open_home_dialog);
        avatar = view.findViewById(R.id.avatar);
        cover_avatar = view.findViewById(R.id.cover_avatar);
        tab_layout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager_2);
        btn_open_cart = view.findViewById(R.id.btn_open_cart);
        cart_count = view.findViewById(R.id.cart_count);
    }
}
