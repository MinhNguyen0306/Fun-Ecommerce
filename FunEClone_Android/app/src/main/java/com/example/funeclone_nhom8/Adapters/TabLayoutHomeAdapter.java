package com.example.funeclone_nhom8.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.funeclone_nhom8.Views.Fragments.ProductPrivateFragment;
import com.example.funeclone_nhom8.Views.Fragments.ProductPublicFragment;
import com.example.funeclone_nhom8.Views.Fragments.Room.AllRoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.Room.JoinedRoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.Room.RequestJoinFragment;

public class TabLayoutHomeAdapter extends FragmentStateAdapter {
    public TabLayoutHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ProductPublicFragment();
            case 1:
                return new ProductPrivateFragment();
            default:
                return new ProductPublicFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
