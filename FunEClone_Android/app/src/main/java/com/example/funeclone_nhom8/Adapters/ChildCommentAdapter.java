package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Models.Comment;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.AppUtil;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Utils.IClickReplyComment;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildCommentAdapter extends RecyclerView.Adapter<ChildCommentAdapter.CommentViewHolder> {

    private Context context;
    List<Comment> comments;

    public ChildCommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    public void setData( List<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }

    public List<Comment> getComments() {
        return comments;
    }

    @NonNull
    @Override
    public ChildCommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildCommentAdapter.CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.username.setText(comment.getUser().getName());
        holder.message.setText(comment.getMessage());
        holder.comment_day.setText(comment.getCreateAt());
        Glide.with(context).load(ConstantUtil.BASE_URL+"user/image/"+comment.getUser().getAvatar()).into(holder.avatar);

        Map.Entry entry = AppUtil.getDifferentBetweenLocalDateTime(comment.getCreateAt());
        if(entry != null) {
            holder.comment_day.setText(entry.getValue() + " " + entry.getKey() + " ago");
        }
    }

    @Override
    public int getItemCount() {
        if(comments != null) {
            return comments.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView message;
        private TextView comment_day;
        private CircleImageView avatar;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            message = itemView.findViewById(R.id.message);
            avatar = itemView.findViewById(R.id.avatar);
            comment_day = itemView.findViewById(R.id.comment_day);
        }
    }
}
