package com.example.popcornapp.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.popcornapp.Models.User;

public class UserHandler {

    private SQLiteHelper dbHelper;

    public UserHandler(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    // INSERT
    public boolean insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_USERNAME, user.getUsername());
        values.put(SQLiteHelper.KEY_EMAIL, user.getEmail());
        values.put(SQLiteHelper.KEY_PASSWORD, user.getPassword());

        long result = db.insert(SQLiteHelper.TABLE_USERS, null, values);
        db.close();

        return result != -1;
    }

    // GET USER BY EMAIL
    public User getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(SQLiteHelper.TABLE_USERS,
                null,
                SQLiteHelper.KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User u = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(SQLiteHelper.KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.KEY_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.KEY_PASSWORD))
            );
            cursor.close();
            return u;
        }

        return null;
    }

    // LOGIN
    public boolean login(String email, String password) {
        User u = getUserByEmail(email);
        if (u == null) return false;
        return u.getPassword().equals(password);
    }
}
