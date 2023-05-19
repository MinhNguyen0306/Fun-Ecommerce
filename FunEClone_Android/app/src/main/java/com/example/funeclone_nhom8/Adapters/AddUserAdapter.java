package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Models.User;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Utils.IClickItemHintUserListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.AddUserViewHolder> {

    private Context context;
    private List<User> users;
    private IClickItemHintUserListener iClickItemHintUserListener;

    public AddUserAdapter(Context context, List<User> users, IClickItemHintUserListener iClickItemHintUserListener) {
        this.context = context;
        this.users = users;
        this.iClickItemHintUserListener = iClickItemHintUserListener;
    }

    @NonNull
    @Override
    public AddUserAdapter.AddUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_user, parent, false);
        return new AddUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddUserViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getName());
        String userAvatar = ConstantUtil.BASE_URL+"user/image/"+user.getAvatar();
        Glide.with(context).load(userAvatar).into(holder.avatar);
        holder.close.setOnClickListener(view -> {
            iClickItemHintUserListener.onClickItemUser(user);
            users.remove(user);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if(users != null) {
            return users.size();
        }
        return 0;
    }

    public class AddUserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView close;
        private CircleImageView avatar;
        private TextView username;

        public AddUserViewHolder(@NonNull View itemView) {
            super(itemView);
            close = itemView.findViewById(R.id.close);
            avatar = itemView.findViewById(R.id.avatar);
            username = itemView.findViewById(R.id.username);
        }
    }
}
