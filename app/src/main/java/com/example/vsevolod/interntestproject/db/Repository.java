package com.example.vsevolod.interntestproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vsevolod.interntestproject.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student Vsevolod on 20.09.17.
 * usevalad.uladzimiravich@gmail.com
 *
 * contains CRUD methods to work with db
 */
public class Repository {

    private DBHelper mDbHelper;
    private ContentValues mContentValues;

    public Repository(Context context) {
        this.mDbHelper = new DBHelper(context);
        this.mContentValues = new ContentValues();
    }

    /**
     * creates user in SQLite db
     */
    public void createUser(User user) {
        mContentValues.put(DBHelper.KEY_FIRST_NAME, user.getFirstName());
        mContentValues.put(DBHelper.KEY_LAST_NAME, user.getLastName());
        mContentValues.put(DBHelper.KEY_BIRTH_DATE, user.getBirthDate());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.insert(DBHelper.TABLE_USERS, null, mContentValues);
        db.close();
    }

    /**
     * get user by id
     *
     * @param id - unique id
     * @return - User or null if it can't find user
     */
    public User readUser(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String where = DBHelper.KEY_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, where, whereArgs, null, null, null);
        User user = null;
        try {
            if (cursor.moveToFirst()) {
                // read column data
                int userId = cursor.getInt(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String birthDate = cursor.getString(3);

                user = new User(userId, firstName, lastName, birthDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }

        return user;
    }

    /**
     * get all users from db
     *
     * @return - list of users
     */
    public List<User> readUsers() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<User> users = new ArrayList<>();
        Cursor cursor = db.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(DBHelper.KEY_ID);
            int firstNameIndex = cursor.getColumnIndex(DBHelper.KEY_FIRST_NAME);
            int lastNameIndex = cursor.getColumnIndex(DBHelper.KEY_LAST_NAME);
            int birthDateIndex = cursor.getColumnIndex(DBHelper.KEY_BIRTH_DATE);

            do {
                int userId = cursor.getInt(index);
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                String birthDate = cursor.getString(birthDateIndex);

                users.add(new User(userId, firstName, lastName, birthDate));
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return users;
    }


    /**
     * updates user fields
     *
     * @param user - user to update
     */
    public void updateUser(User user) {
        mContentValues.put(DBHelper.KEY_FIRST_NAME, user.getFirstName());
        mContentValues.put(DBHelper.KEY_LAST_NAME, user.getLastName());
        mContentValues.put(DBHelper.KEY_BIRTH_DATE, user.getBirthDate());

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.update(DBHelper.TABLE_USERS, mContentValues, DBHelper.KEY_ID + "=" + user.getId(), null);
        db.close();
    }


    /**
     * Deleting data row from db by id
     *
     * @param id - unique id
     */
    public void deleteUser(int id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_USERS, DBHelper.KEY_ID + "=" + id, null);
        db.close();
    }

    /**
     * adding 20 dummy users
     */
    public void addDummyUsers() {
        for (int i = 0; i < 20; i++) {
            createUser(new User("User", String.valueOf(i), "05/05/05"));
        }
    }
}
