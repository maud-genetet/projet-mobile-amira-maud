package com.example.popcornapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popcornapp.MainActivity;
import com.example.popcornapp.Managers.SessionManager;
import com.example.popcornapp.Managers.UserHandler;
import com.example.popcornapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button btnLogin;
    private TextView txtGoToRegister;

    private UserHandler userHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtGoToRegister = findViewById(R.id.txtGoToRegister);

        userHandler = new UserHandler(this);

        txtGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            // On vérifie une seule fois
            boolean success = userHandler.login(email, password);

            if (success) {
                Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                // Sauvegarde de la session
                SessionManager sessionManager = new SessionManager(LoginActivity.this);
                sessionManager.saveUser(email);

                // Redirection
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
