package com.example.funeclone_nhom8.Views.Fragments.Room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funeclone_nhom8.Adapters.RoomAdapter;
import com.example.funeclone_nhom8.Datas.Models.Group;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.IClickItemRoomListener;

import java.util.ArrayList;
import java.util.List;

public class AllRoomFragment extends Fragment {

    public AllRoomFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_room, container, false);

        RecyclerView rcv_room = view.findViewById(R.id.rcv_room);

        RoomAdapter roomAdapter = new RoomAdapter(this.getContext(), group -> {

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        rcv_room.setLayoutManager(linearLayoutManager);
        rcv_room.addItemDecoration(itemDecoration);
        rcv_room.setAdapter(roomAdapter);

        return view;
    }

    private List<Group> getRoomData() {
        List<Group> groups = new ArrayList<>();
//        groups.add(new Group(1, "Room1", R.drawable.hoaanhdao));
//        groups.add(new Group(2, "Room2", R.drawable.hoacuc));
//        groups.add(new Group(3, "Room3", R.drawable.hoaanhdao));
//        groups.add(new Group(4, "Room4", R.drawable.hoaanhdao));
//        groups.add(new Group(5, "Room5", R.drawable.hoaanhdao));
//        groups.add(new Group(6, "Room6", R.drawable.hoaanhdao));
//        groups.add(new Group(7, "Room7", R.drawable.hoaanhdao));
        return groups;
    }
}
