package com.example.mathstatistic1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(MainActivity.LOG_TAG, "---Table created---");
        db.execSQL("create table settingsValues(" +
                "id integer primary key," +
                "koefVariac real," +
                "googleLensChoice text" +
                ");");
        db.execSQL("insert into settingsValues(" +
                "koefVariac, googleLensChoice" +
                ")" +
                "values (" +
                "10, 'None'" +
                ");");
        Log.d(MainActivity.LOG_TAG, "---Table created success---");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
