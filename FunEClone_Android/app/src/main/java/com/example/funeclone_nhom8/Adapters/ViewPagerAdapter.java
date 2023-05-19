package com.example.funeclone_nhom8.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.funeclone_nhom8.Views.Fragments.AccountFragment;
import com.example.funeclone_nhom8.Views.Fragments.HomeFragment;
import com.example.funeclone_nhom8.Views.Fragments.RoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.RoomManageFragment;
import com.example.funeclone_nhom8.Views.Fragments.SearchFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new RoomManageFragment();
            case 3:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
