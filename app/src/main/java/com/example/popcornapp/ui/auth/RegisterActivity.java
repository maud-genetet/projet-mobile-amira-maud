package com.example.popcornapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popcornapp.Managers.UserHandler;
import com.example.popcornapp.Models.User;
import com.example.popcornapp.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editUsername, editEmail, editPassword;
    private Button btnRegister;
    private TextView txtGoToLogin;

    private UserHandler userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtGoToLogin = findViewById(R.id.txtGoToLogin);

        userHandler = new UserHandler(this);

        txtGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        btnRegister.setOnClickListener(v -> {

            String username = editUsername.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(username, email, password);

            boolean inserted = userHandler.insertUser(user);

            if (inserted) {
                Toast.makeText(this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Erreur : email déjà existant", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
