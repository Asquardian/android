package com.example.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Database extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "LabDB";
    private SQLiteDatabase myDataBase;
    private final Context mContext;

    public Database(Context context) {
        super(context, DB_NAME, null, 1);
        DB_PATH = getFileFromAssets(context, "fileName.extension").absolutePath
        String myPath = DB_PATH + DB_NAME;
        this.mContext = context;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public String open(){
        Cursor c = myDataBase.rawQuery("select * from Human", null);
        String data = null;
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                data = c.getString(c.getColumnIndex("name"));

                c.moveToNext();
            }
        }
        c.close();
        return data;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
