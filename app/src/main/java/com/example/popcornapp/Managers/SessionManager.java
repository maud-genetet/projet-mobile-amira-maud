package com.example.popcornapp.Managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "user_session";
    private static final String KEY_EMAIL = "email";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Sauvegarder l'email de l'utilisateur connecté
    public void saveUser(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    // Récupérer l'utilisateur connecté
    public String getUserEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    // Vérifier si quelqu'un est connecté
    public boolean isLogged() {
        return getUserEmail() != null;
    }

    // Déconnexion
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
