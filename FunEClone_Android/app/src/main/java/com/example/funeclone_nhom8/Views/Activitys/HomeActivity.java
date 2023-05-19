package com.example.funeclone_nhom8.Views.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.funeclone_nhom8.Adapters.ViewPagerAdapter;
import com.example.funeclone_nhom8.Datas.Apis.CartApi;
import com.example.funeclone_nhom8.Datas.DataLocal.DataLocalManager;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Views.Fragments.AccountFragment;
import com.example.funeclone_nhom8.Views.Fragments.HomeFragment;
import com.example.funeclone_nhom8.Views.Fragments.RoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.RoomManageFragment;
import com.example.funeclone_nhom8.Views.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();

        User user = DataLocalManager.getUser();
        if(user != null) {
            CartApi.cartApi.getSizeCart(user.getId()).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    DataLocalManager.setCountCart(response.body());
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e("Cart Api Line 49 at HomeActivity Error", t.getMessage());
                }
            });
        }

        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_nav, homeFragment);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager()
                            .beginTransaction().replace(R.id.content_nav, new HomeFragment());
                    fragmentTransaction1.commit();
                    break;
                case R.id.search:
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager()
                            .beginTransaction().replace(R.id.content_nav, new SearchFragment());
                    fragmentTransaction2.commit();
                    break;
                case R.id.room:
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager()
                            .beginTransaction().replace(R.id.content_nav, new RoomManageFragment());
                    fragmentTransaction3.commit();
                    break;
                case R.id.account:
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager()
                            .beginTransaction().replace(R.id.content_nav, new AccountFragment());
                    fragmentTransaction4.commit();
                    break;
            }
            return true;
        });

        setUpViewPager();
    }

    private void initUI(){
        bottomNavigationView = findViewById(R.id.nav_footer);
    }

    private void setUpViewPager() {

    }
}