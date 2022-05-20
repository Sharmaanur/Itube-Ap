package com.suman.itube.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "clientDB";
    private static final String TABLE = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    public UserDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FULLNAME + " TEXT" + ","
                + KEY_USERNAME + " TEXT UNIQUE,"
                + KEY_PASSWORD + " TEXT" + ");";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addUser(UserModel clientIDModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, clientIDModel.getFullname());
        values.put(KEY_USERNAME, clientIDModel.getUsername());
        values.put(KEY_PASSWORD, clientIDModel.getPassword());
        // Inserting Row
        String selectQuery = "SELECT * FROM " + TABLE + " WHERE " + KEY_USERNAME + " = '"+ clientIDModel.getUsername()+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() < 1) {
            db.insert(TABLE, null, values);
        }else{
            updateClient(clientIDModel);
        }
        //db.close();
    }

    public boolean validateUser(UserModel userModel) {
        boolean res = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE + " WHERE " + KEY_USERNAME + " = '"+ userModel.getUsername()+"' AND "+KEY_PASSWORD+" = '"+userModel.getPassword()+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            res = true;
        }
        return res;
    }

    public int updateClient(UserModel clientIDModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FULLNAME, clientIDModel.getFullname());
        values.put(KEY_USERNAME, clientIDModel.getUsername());
        values.put(KEY_PASSWORD, clientIDModel.getPassword());

        // updating row
        return db.update(TABLE, values, KEY_USERNAME + " = ?",
                new String[] { String.valueOf(clientIDModel.getUsername()) });
    }

    public void deleteClient(UserModel clientIDModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, KEY_USERNAME + " = ?",
                new String[] { String.valueOf(clientIDModel.getUsername()) });
        db.close();
    }
}
