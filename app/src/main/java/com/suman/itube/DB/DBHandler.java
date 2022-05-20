package com.suman.itube.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "clientvDB";
    private static final String TABLE = "video";
    private static final String KEY_ID = "id";
    private static final String KEY_CLIENT = "link";
    private static final String KEY_TIME = "time";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CLIENT + " TEXT UNIQUE,"
                + KEY_TIME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addVideos(VideoModel clientIDModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT, clientIDModel.getVideoLink());
        values.put(KEY_TIME, System.currentTimeMillis());
        // Inserting Row

        String selectQuery = "SELECT * FROM " + TABLE + " WHERE " +KEY_CLIENT+ " = '"+ clientIDModel.getVideoLink()+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() < 1) {
            db.insert(TABLE, null, values);
        }else{
            updateClient(clientIDModel);
        }
        //2nd argument is String containing nullColumnHack
        //db.close(); // Closing database connection
    }

    public List<VideoModel> getAllVideos() {
        List<VideoModel> contactList = new ArrayList<VideoModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE + " ORDER BY"+ " time" + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VideoModel clientIDModel = new VideoModel();
                clientIDModel.setId(cursor.getString(0));
                clientIDModel.setVideoLink(cursor.getString(1));
                clientIDModel.setTime(cursor.getString(2));
                // Adding contact to list
                contactList.add(clientIDModel);
            } while (cursor.moveToNext());
        }

        // return list
        return contactList;
    }

    public int updateClient(VideoModel clientIDModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, System.currentTimeMillis());

        // updating row
        return db.update(TABLE, values, KEY_CLIENT + " = ?",
                new String[] { String.valueOf(clientIDModel.getVideoLink()) });
    }

    public void deleteClient(VideoModel clientIDModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, KEY_CLIENT + " = ?",
                new String[] { String.valueOf(clientIDModel.getVideoLink()) });
        db.close();
    }
}
