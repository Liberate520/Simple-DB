package ru.samsung.simpledb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class MyDB {
    private static String DATABASE_NAME = "simple.db";
    private static String TABLE_NAME = "person";
    private static int DATABASE_VERSION = 1;

    private String COLUMN_ID = "_id";
    private String COLUMN_NAME = "name";
    private String COLUMN_POINTS = "points";

    private int NUM_COLUMN_ID = 0;
    private int NUM_COLUMN_NAME = 1;
    private int NUM_COLUMN_POINTS = 2;

    private SQLiteDatabase database;

    public MyDB(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        database = mOpenHelper.getWritableDatabase();
    }

    class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "create table " + TABLE_NAME + " (" +
                    COLUMN_ID + " integer primary key autoincrement not null, " +
                    COLUMN_NAME + " text not null, " +
                    COLUMN_POINTS + " integer not null);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
        }
    }
}
