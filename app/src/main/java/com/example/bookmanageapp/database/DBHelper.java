package com.example.bookmanageapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BookManageDB.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBQuery.SQL_CREATE_USERS_ENTRIES);
        db.execSQL(DBQuery.SQL_CREATE_BOOK_ENTRIES);
        db.execSQL(DBQuery.SQL_CREATE_MESSAGES_ENTRIES);
        db.execSQL(DBQuery.SQL_CREATE_READINGHISTORY_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBQuery.SQL_DELETE_USERS_ENTRIES);
        db.execSQL(DBQuery.SQL_DELETE_BOOK_ENTRIES);
        db.execSQL(DBQuery.SQL_DELETE_MESSAGES_ENTRIES);
        db.execSQL(DBQuery.SQL_DELETE_READINGHISTORY_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
