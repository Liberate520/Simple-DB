package ru.samsung.simpledb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public Person select(int id){
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_ID + "=" + id, null, null, null, null);
        Person person;
        if (cursor.moveToFirst()) {
            person = new Person();
            person.id = cursor.getLong(NUM_COLUMN_ID);
            person.name = cursor.getString(NUM_COLUMN_NAME);
            person.points = cursor.getInt(NUM_COLUMN_POINTS);
            cursor.close();
            return person;
        }
        cursor.close();
        return null;
    }

    public List<Person> selectAll(){
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Person> list = new ArrayList<>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                Person person = new Person();
                person.id = cursor.getLong(NUM_COLUMN_ID);
                person.name = cursor.getString(NUM_COLUMN_NAME);
                person.points = cursor.getInt(NUM_COLUMN_POINTS);
                list.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insert(List<Person> list){
        if (list.size() == 0){
            return 0;
        }
        long count = 0;
        for (int i = 0; i < list.size(); i++) {
            Person person = list.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, person.name);
            contentValues.put(COLUMN_POINTS, person.points);
            database.insert(TABLE_NAME, null, contentValues);
            count++;
        }
        return count;
    }

    public long insert(Person person){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, person.name);
        contentValues.put(COLUMN_POINTS, person.points);
        return database.insert(TABLE_NAME, null, contentValues);
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
