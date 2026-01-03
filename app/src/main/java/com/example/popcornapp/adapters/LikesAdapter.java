package com.example.popcornapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornapp.Managers.LikesHandler;
import com.example.popcornapp.Models.Like;
import com.example.popcornapp.R;

import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.LikeViewHolder> {

    private List<Like> likes;
    private LikesHandler likesHandler;
    private OnLikeRemovedListener onLikeRemovedListener;

    public interface OnLikeRemovedListener {
        void onLikeRemoved(int position);
    }

    public LikesAdapter(List<Like> likes, LikesHandler likesHandler, OnLikeRemovedListener listener) {
        this.likes = likes;
        this.likesHandler = likesHandler;
        this.onLikeRemovedListener = listener;
    }

    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false);
        return new LikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {
        Like like = likes.get(position);

        holder.tvTitle.setText(like.getTitle());

        holder.btnRemove.setOnClickListener(v -> {
            likesHandler.removeLike(like.getUserId(), like.getItemId());
            likes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, likes.size());

            if (onLikeRemovedListener != null) {
                onLikeRemovedListener.onLikeRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return likes.size();
    }

    public static class LikeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        Button btnRemove;

        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
