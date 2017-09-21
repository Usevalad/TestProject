package com.example.vsevolod.interntestproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Student Vsevolod on 21.09.17.
 * usevalad.uladzimiravich@gmail.com
 */

class DBHelper extends SQLiteOpenHelper {

    //database constants
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "usersDatabase";
    static final String TABLE_USERS = "users";
    static final String KEY_ID = "_id";
    static final String KEY_FIRST_NAME = "firstName";
    static final String KEY_LAST_NAME = "lastName";
    static final String KEY_BIRTH_DATE = "birthDate";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL = String.format("create table %s(%s integer primary key autoincrement," +
                        " %s text, %s text, %s text)",
                TABLE_USERS,
                KEY_ID,
                KEY_FIRST_NAME,
                KEY_LAST_NAME,
                KEY_BIRTH_DATE);

        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_USERS);

        onCreate(sqLiteDatabase);
    }
}
