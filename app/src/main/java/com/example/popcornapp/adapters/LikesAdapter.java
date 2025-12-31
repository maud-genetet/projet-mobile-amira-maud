package com.example.popcornapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornapp.Managers.LikesDAO;
import com.example.popcornapp.R;

import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.LikeViewHolder> {

    private List<LikesDAO.Like> likes;
    private LikesDAO likesDAO;
    private OnLikeRemovedListener onLikeRemovedListener;

    public interface OnLikeRemovedListener {
        void onLikeRemoved(int position);
    }

    public LikesAdapter(List<LikesDAO.Like> likes, LikesDAO likesDAO, OnLikeRemovedListener listener) {
        this.likes = likes;
        this.likesDAO = likesDAO;
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
        LikesDAO.Like like = likes.get(position);

        holder.tvItemId.setText("Film ID: " + like.getItemId());
        holder.tvItemType.setText("Type: " + like.getItemType());

        holder.btnRemove.setOnClickListener(v -> {
            likesDAO.removeLike(like.getUserId(), like.getItemId());
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
        TextView tvItemId, tvItemType;
        Button btnRemove;

        public LikeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemId = itemView.findViewById(R.id.tvItemId);
            tvItemType = itemView.findViewById(R.id.tvItemType);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
