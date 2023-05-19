package com.example.funeclone_nhom8.Views.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Views.Activitys.ProfileActivity;

public class AccountFragment extends Fragment {

    private LinearLayout open_profile_activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        open_profile_activity = view.findViewById(R.id.open_profile_activity);

        open_profile_activity.setOnClickListener(view1 -> openProfileActivity());

        return view;
    }

    private void openProfileActivity() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }
}
