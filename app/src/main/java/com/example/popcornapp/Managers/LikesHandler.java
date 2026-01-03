package com.example.popcornapp.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.popcornapp.Models.Like;

import java.util.ArrayList;
import java.util.List;

public class LikesHandler {

    private final SQLiteHelper dbHelper;

    public LikesHandler(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public boolean addLike(int userId, String itemId, String title) {
        if (isLiked(userId, itemId)) {
            return false;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("item_id", itemId);
        values.put("title", title);


        long result = db.insert(SQLiteHelper.TABLE_LIKES, null, values);
        db.close();

        return result != -1;
    }

    public boolean isLiked(int userId, String itemId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                SQLiteHelper.TABLE_LIKES,
                null,
                "user_id = ? AND item_id = ?",
                new String[]{String.valueOf(userId), itemId},
                null, null, null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }

    public boolean removeLike(int userId, String itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int result = db.delete(
                SQLiteHelper.TABLE_LIKES,
                "user_id = ? AND item_id = ?",
                new String[]{String.valueOf(userId), itemId}
        );

        db.close();

        return result > 0;
    }

    public List<Like> getLikesForUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Like> results = new ArrayList<>();

        Cursor cursor = db.query(
                SQLiteHelper.TABLE_LIKES,
                new String[]{"id", "item_id", "title"},
                "user_id = ?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int likeId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String itemId = cursor.getString(cursor.getColumnIndexOrThrow("item_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));

                results.add(new Like(likeId, userId, itemId, title));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return results;
    }
}
