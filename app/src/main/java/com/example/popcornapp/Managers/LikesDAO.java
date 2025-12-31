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

    public boolean addLike(int userId, String itemId, String itemType) {
        if (isLiked(userId, itemId)) {
            return false;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("item_id", itemId);
        values.put("item_type", itemType);

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
                new String[]{"id", "item_id", "item_type"},
                "user_id = ?",
                new String[]{String.valueOf(userId)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                int likeId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String itemId = cursor.getString(cursor.getColumnIndexOrThrow("item_id"));
                String itemType = cursor.getString(cursor.getColumnIndexOrThrow("item_type"));

                results.add(new Like(likeId, userId, itemId, itemType));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return results;
    }

    // Classe interne pour représenter un Like
    public static class Like {
        private int id;
        private int userId;
        private String itemId;
        private String itemType;

        public Like(int id, int userId, String itemId, String itemType) {
            this.id = id;
            this.userId = userId;
            this.itemId = itemId;
            this.itemType = itemType;
        }

        public int getId() { return id; }
        public int getUserId() { return userId; }
        public String getItemId() { return itemId; }
        public String getItemType() { return itemType; }

        public String toString() {
            return "Like{" +
                    "id=" + id + "";

        }
    }
}
