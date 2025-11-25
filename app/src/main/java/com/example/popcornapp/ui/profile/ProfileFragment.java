package com.example.popcornapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.popcornapp.Managers.LikesDAO;
import com.example.popcornapp.Managers.SessionManager;
import com.example.popcornapp.Models.User;
import com.example.popcornapp.Managers.UserHandler;
import com.example.popcornapp.R;

import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView txtUsername, txtEmail;
    private LinearLayout containerLikes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        containerLikes = view.findViewById(R.id.containerLikes);

        loadUserProfile();

        return view;
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
        List<String> likes = likesDAO.getLikesForUser(user.getId());

        containerLikes.removeAllViews();

        for (String item : likes) {
            TextView t = new TextView(requireContext());
            t.setText("• " + item);
            t.setTextSize(16f);
            t.setPadding(0, 8, 0, 8);
            containerLikes.addView(t);
        }
    }
}
