package com.example.popcornapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.popcornapp.Managers.SessionManager;
import com.example.popcornapp.Managers.UserHandler;
import com.example.popcornapp.Models.User;
import com.example.popcornapp.ui.auth.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.popcornapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ---- VÉRIFIER SESSION ----
        SessionManager sessionManager = new SessionManager(this);
        if (!sessionManager.isLogged()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // ---- AFFICHAGE UI ----
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // ---- DESTINATIONS ----
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(
                this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // ---- AFFICHER NOM + EMAIL DANS LE HEADER ----
        View header = navigationView.getHeaderView(0);

        TextView headerUsername = header.findViewById(R.id.headerUsername);
        TextView headerEmail = header.findViewById(R.id.headerEmail);

        String email = sessionManager.getUserEmail();

        UserHandler userHandler = new UserHandler(this);
        User currentUser = userHandler.getUserByEmail(email);

        if (currentUser != null) {
            headerUsername.setText(currentUser.getUsername());
            headerEmail.setText(currentUser.getEmail());
        }

        // ---- LOGOUT ----
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.logout) {

                SessionManager sessionOut = new SessionManager(MainActivity.this);
                sessionOut.logout();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }

            // Permet de garder la navigation intacte
            item.setChecked(true);

            return NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
