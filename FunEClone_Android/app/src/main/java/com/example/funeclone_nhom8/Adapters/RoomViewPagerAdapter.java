package com.example.funeclone_nhom8.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.funeclone_nhom8.Views.Fragments.AccountFragment;
import com.example.funeclone_nhom8.Views.Fragments.HomeFragment;
import com.example.funeclone_nhom8.Views.Fragments.Room.AllRoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.Room.JoinedRoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.Room.RequestJoinFragment;
import com.example.funeclone_nhom8.Views.Fragments.RoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.SearchFragment;
import com.google.android.material.tabs.TabLayoutMediator;

public class RoomViewPagerAdapter extends FragmentStateAdapter {

    public RoomViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new JoinedRoomFragment();
            case 1:
                return new RequestJoinFragment();
            default:
                return new JoinedRoomFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
