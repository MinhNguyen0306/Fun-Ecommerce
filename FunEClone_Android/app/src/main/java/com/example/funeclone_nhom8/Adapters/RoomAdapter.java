package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Models.Group;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Utils.IClickItemRoomListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private Context context;
    private List<Group> groups;
    private IClickItemRoomListener iClickItemRoomListener;

    public RoomAdapter(Context context, IClickItemRoomListener iClickItemRoomListener) {
        this.context = context;
        this.iClickItemRoomListener = iClickItemRoomListener;
    }

    public void setData(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getData() {
        return this.groups;
    }

    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.room_name.setText(group.getTitle());
        Glide.with(context).load(ConstantUtil.BASE_URL+"group/image/"+group.getAvatar()).into(holder.room_image);
        holder.item_room.setOnClickListener(view -> {
            iClickItemRoomListener.onClickItemRoom(group);
        });
    }

    @Override
    public int getItemCount() {
        if(groups != null) {
            return groups.size();
        }
        return 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView room_image;
        private TextView room_name;
        private LinearLayout item_room;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            room_image = itemView.findViewById(R.id.room_image);
            room_name = itemView.findViewById(R.id.room_name);
            item_room = itemView.findViewById(R.id.item_room);
        }
    }
}
