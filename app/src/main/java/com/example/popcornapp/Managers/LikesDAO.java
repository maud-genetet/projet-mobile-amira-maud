package com.example.popcornapp.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LikesDAO {

    private final SQLiteHelper dbHelper;

    public LikesDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    // AJOUTER UN LIKE
    public void addLike(int userId, String itemId, String itemType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("item_id", itemId);
        values.put("item_type", itemType);

        db.insert(SQLiteHelper.TABLE_LIKES, null, values);
        db.close();
    }

    // RECUPERER LES LIKES POUR UN UTILISATEUR
    public List<String> getLikesForUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> results = new ArrayList<>();

        Cursor cursor = db.query(
                SQLiteHelper.TABLE_LIKES,
                new String[]{"item_id", "item_type"},
                "user_id = ?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                String itemId = cursor.getString(cursor.getColumnIndexOrThrow("item_id"));
                String itemType = cursor.getString(cursor.getColumnIndexOrThrow("item_type"));

                results.add(itemId + " (" + itemType + ")");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return results;
    }
}
