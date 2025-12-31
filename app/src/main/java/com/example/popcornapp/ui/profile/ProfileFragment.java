package com.example.popcornapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popcornapp.Managers.LikesDAO;
import com.example.popcornapp.Managers.SessionManager;
import com.example.popcornapp.Models.User;
import com.example.popcornapp.Managers.UserHandler;
import com.example.popcornapp.R;
import com.example.popcornapp.adapters.LikesAdapter;

import java.util.List;

public class ProfileFragment extends Fragment implements LikesAdapter.OnLikeRemovedListener {

    private TextView txtUsername, txtEmail, tvNoLikes;
    private RecyclerView recyclerViewLikes;
    private LikesAdapter likesAdapter;
    private List<LikesDAO.Like> likesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        recyclerViewLikes = view.findViewById(R.id.recyclerViewLikes);
        tvNoLikes = view.findViewById(R.id.tvNoLikes);

        setupRecyclerView();
        loadUserProfile();

        return view;
    }

    private void setupRecyclerView() {
        recyclerViewLikes.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void loadUserProfile() {
        // 1) Get session email
        SessionManager sessionManager = new SessionManager(requireContext());
        String email = sessionManager.getUserEmail();

        // 2) Load user from DB
        UserHandler userHandler = new UserHandler(requireContext());
        User user = userHandler.getUserByEmail(email);

        if (user != null) {
            txtUsername.setText(user.getUsername());
            txtEmail.setText(user.getEmail());
        }

            // 3) Load Likes
            LikesDAO likesDAO = new LikesDAO(requireContext());
            likesList = likesDAO.getLikesForUser(user.getId());

            // 4) Setup adapter
            if (likesList != null && !likesList.isEmpty()) {
                tvNoLikes.setVisibility(View.GONE);
                recyclerViewLikes.setVisibility(View.VISIBLE);

                likesAdapter = new LikesAdapter(likesList, likesDAO, this);
                recyclerViewLikes.setAdapter(likesAdapter);
            } else {
                recyclerViewLikes.setVisibility(View.GONE);
                tvNoLikes.setVisibility(View.VISIBLE);
                tvNoLikes.setText("Aucun film aimé pour le moment");
            }
        }
    }

    @Override
    public void onLikeRemoved(int position) {
        if (likesList.isEmpty()) {
            recyclerViewLikes.setVisibility(View.GONE);
            tvNoLikes.setVisibility(View.VISIBLE);
        }
        Toast.makeText(requireContext(), "Like supprimé", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
