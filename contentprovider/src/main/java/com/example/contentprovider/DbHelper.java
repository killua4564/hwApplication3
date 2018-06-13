package com.example.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contentprovider.Contract.Entry;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sqlite.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + Entry.TABLE_NAME + " (" +
                Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Entry.COLUMN_NAME + " TEXT NOT NULL, " +
                Entry.COLUMN_PHONE + " TEXT NOT NULL, " +
                Entry.COLUMN_PEOPLE + " TEXT NOT NULL, " +
                Entry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
