package com.example.popcornapp.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Like {
    private int id;
    private int userId;
    private String itemId;
    private String title;

    public Like(int id, int userId, String itemId, String title) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return "Like{" +
                "id=" + id + "";

    }
}