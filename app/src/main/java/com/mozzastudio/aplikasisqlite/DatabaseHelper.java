package com.mozzastudio.aplikasisqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "DBSQLITE";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_ALAMAT = "alamat";
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAMA + " TEXT," + KEY_ALAMAT + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("table", CREATE_TABLE_USERS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USERS + "'");
        onCreate(db);
    }

    public long addStudentDetail(String nama, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAMA, nama);
        contentValues.put(KEY_ALAMAT, alamat);

        long insert = db.insert(TABLE_USERS, null, contentValues);
        return insert;
    }

    public ArrayList<Map<String, Object>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Map<String, Object>> userList = new ArrayList<>();
        String nama = "";
        String alamat = "";
        int id = 0;
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                nama = cursor.getString(cursor.getColumnIndex(KEY_NAMA));
                alamat = cursor.getString(cursor.getColumnIndex(KEY_ALAMAT));
                Map<String, Object> listItemMap = new HashMap<>();
                listItemMap.put("id", id);
                listItemMap.put("nama", nama);
                listItemMap.put("alamat", alamat);
                userList.add(listItemMap);
            }
            while (cursor.moveToNext());
        }
        return userList;
    }

    public void update(int id, String nama, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_USERS + " SET " + KEY_NAMA + "='" + nama + "'," + KEY_ALAMAT + "='" + alamat + "' WHERE " + KEY_ID + "='" + id + "'";
        db.execSQL(updateQuery);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_USERS + " WHERE " + KEY_ID + "='" + id + "'";
        db.execSQL(deleteQuery);
        db.close();
    }
}


