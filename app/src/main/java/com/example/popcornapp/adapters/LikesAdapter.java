package com.example.popcornapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popcornapp.MovieDetailActivity;
import com.example.popcornapp.Managers.LikesHandler;
import com.example.popcornapp.Models.Like;
import com.example.popcornapp.R;
import com.example.popcornapp.adapters.LikeViewHolder;

import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikeViewHolder> {

    private List<Like> likes;
    private LikesHandler likesHandler;
    private OnLikeRemovedListener onLikeRemovedListener;
    private Context context;

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
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_like, parent, false);
        return new LikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {
        Like like = likes.get(position);

        holder.tvTitle.setText(like.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra("MOVIE_ID", like.getItemId());
            context.startActivity(intent);
        });

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

    
}
