package com.example.funeclone_nhom8.Adapters;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.funeclone_nhom8.Datas.Apis.CommentApi;
import com.example.funeclone_nhom8.Datas.Models.Comment;
import com.example.funeclone_nhom8.R;
import com.example.funeclone_nhom8.Utils.AppUtil;
import com.example.funeclone_nhom8.Utils.ConstantUtil;
import com.example.funeclone_nhom8.Utils.IClickReplyComment;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    List<Comment> comments;
    List<Comment> childComments;
    ChildCommentAdapter childCommentAdapter;
    IClickReplyComment iClickReplyComment;

    public CommentAdapter(Context context, List<Comment> comments, IClickReplyComment iClickReplyComment) {
        this.context = context;
        this.comments = comments;
        this.childComments = new ArrayList<>();
        childCommentAdapter = new ChildCommentAdapter(context, childComments);
        this.iClickReplyComment = iClickReplyComment;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }

    public void addChildComment(Comment childComment){
        this.childComments.add(childComment);
        childCommentAdapter.notifyDataSetChanged();
    }

    public List<Comment> getComments() {
        return comments;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.username.setText(comment.getUser().getName());
        holder.message.setText(comment.getMessage());

        Map.Entry entry = AppUtil.getDifferentBetweenLocalDateTime(comment.getCreateAt());
        if(entry != null) {
            holder.comment_day.setText(entry.getValue() + " " + entry.getKey() + " ago");
        }

        Glide.with(context).load(ConstantUtil.BASE_URL+"user/image/"+comment.getUser().getAvatar()).into(holder.avatar);

        holder.reply.setOnClickListener(view -> {
            iClickReplyComment.onClickOpenComment(comment);
        });

        CommentApi.commentApi.getAllChildComment(comment.getId()).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> childComments = response.body();
                if(childComments != null && !childComments.isEmpty()) {
                    setChildComments(childComments);
                    holder.see_replies.setVisibility(View.VISIBLE);
                    holder.txt_number_replies.setText(String.valueOf(childComments.size()));
                    holder.see_replies.setOnClickListener(view -> {
                        holder.rcv_child_comment.setVisibility(View.VISIBLE);
                        childCommentAdapter.setData(childComments);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        holder.rcv_child_comment.setLayoutManager(linearLayoutManager);
                        holder.rcv_child_comment.setAdapter(childCommentAdapter);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("Child Comment APi Error", t.getMessage());
            }
        });

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
        private TextView txt_number_replies;
        private RecyclerView rcv_child_comment;
        private TextView reply;
        private CircleImageView avatar;
        private LinearLayout see_replies;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            message = itemView.findViewById(R.id.message);
            reply = itemView.findViewById(R.id.reply);
            avatar = itemView.findViewById(R.id.avatar);
            comment_day = itemView.findViewById(R.id.comment_day);
            txt_number_replies = itemView.findViewById(R.id.txt_number_replies);
            rcv_child_comment = itemView.findViewById(R.id.rcv_child_comment);
            see_replies = itemView.findViewById(R.id.see_replies);
        }
    }
}
