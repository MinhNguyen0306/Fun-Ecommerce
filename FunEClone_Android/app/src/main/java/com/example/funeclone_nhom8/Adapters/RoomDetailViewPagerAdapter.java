package com.example.funeclone_nhom8.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.funeclone_nhom8.Views.Fragments.Room.JoinedRoomFragment;
import com.example.funeclone_nhom8.Views.Fragments.Room.RequestJoinFragment;
import com.example.funeclone_nhom8.Views.Fragments.RoomPrivatePostFragment;
import com.example.funeclone_nhom8.Views.Fragments.RoomPublicPostFragment;

public class RoomDetailViewPagerAdapter extends FragmentStateAdapter {

    private Integer groupId;

    public RoomDetailViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Integer groupId) {
        super(fragmentActivity);
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("groupId", groupId);
                RoomPrivatePostFragment roomPrivatePostFragment = new RoomPrivatePostFragment();
                roomPrivatePostFragment.setArguments(bundle2);
                return roomPrivatePostFragment;
            default:
                Bundle bundle = new Bundle();
                bundle.putInt("groupId", groupId);
                RoomPublicPostFragment roomPublicPostFragment = new RoomPublicPostFragment();
                roomPublicPostFragment.setArguments(bundle);
                return roomPublicPostFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
