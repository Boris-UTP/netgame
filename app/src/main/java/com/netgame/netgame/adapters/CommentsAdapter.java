package com.netgame.netgame.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netgame.netgame.R;
import com.netgame.netgame.models.Comment;

import java.util.List;

/**
 * Created by arkanay on 10/11/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public CommentsAdapter setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(holder.itemView.getLayoutParams().width,
                holder.itemView.getLayoutParams().height);

        if (position == comments.size() -1 ){
            params.setMargins(0,30,0,30);
        } else{
            params.setMargins(0,30,0,0);
        }

        if (comment.getFlagUser() == 0) {
            params.setMarginStart(70);
            params.setMarginEnd(30);
        } else {
            params.setMarginEnd(70);
            params.setMarginStart(30);
        }

        holder.itemView.setLayoutParams(params);

        holder.userNameTextView.setText(comment.getUserName());
        holder.descriptionTextView.setText(comment.getDescription());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTextView;
        TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

        }
    }
}
