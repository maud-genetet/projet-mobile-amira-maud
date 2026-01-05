package com.example.popcornapp.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.example.popcornapp.R;

public class LikeViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    Button btnRemove;

    public LikeViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        btnRemove = itemView.findViewById(R.id.btnRemove);
    }
}