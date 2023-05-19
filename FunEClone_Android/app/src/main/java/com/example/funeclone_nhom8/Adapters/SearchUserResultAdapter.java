package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

public class SearchUserResultAdapter extends RecyclerView.Adapter<SearchUserResultAdapter.SearchUserResultViewHolder> {
    private Context context;
    private List<User> users;
    private IClickItemHintUserListener iClickItemHintUserListener;

    public SearchUserResultAdapter(Context context, List<User> users, IClickItemHintUserListener iClickItemHintUserListener) {
        this.context = context;
        this.users = users;
        this.iClickItemHintUserListener = iClickItemHintUserListener;
    }

    @NonNull
    @Override
    public SearchUserResultAdapter.SearchUserResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_user_result, parent, false);
        return new SearchUserResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserResultAdapter.SearchUserResultViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getName());
        holder.email.setText(user.getEmail());
        Glide.with(context).load(ConstantUtil.BASE_URL+"user/image/"+user.getAvatar()).into(holder.avatar);
        holder.item_user_hint.setOnClickListener(view -> {
            iClickItemHintUserListener.onClickItemUser(user);
        });
    }

    @Override
    public int getItemCount() {
        if(users != null) {
            return users.size();
        }
        return 0;
    }

    public class SearchUserResultViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView avatar;
        private TextView username;
        private TextView email;
        private RelativeLayout item_user_hint;

        public SearchUserResultViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
            item_user_hint = itemView.findViewById(R.id.item_user_hint);
        }
    }

    public void filteredList(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}
