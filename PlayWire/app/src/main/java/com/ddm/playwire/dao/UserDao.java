package com.ddm.playwire.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ddm.playwire.model.User;
import com.ddm.playwire.repository.UserRepository;

public class UserDao implements UserRepository {

    private final SQLiteManager sqlLiteManager;
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public UserDao(@Nullable Context context) {
        this.sqlLiteManager = new SQLiteManager(context);
    }

    @Override
    public User insertUser(@NonNull User user) {
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(result);
        user.setUserId(Integer.parseInt(String.valueOf(result)));

        return user;
    }

    @Override
    public User loadUserByCredentials(String username, String password){

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '"+ username + "' AND " + COLUMN_PASSWORD + " = '" + password + "'";
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();
        User user = null;

        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){
                int userId = Integer.parseInt(cursor.getString(0));
                user = new User(userId, username, password);
            }
        }

        return user;
    }

    @Override
    public User loadUserById(int userId){

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = "+ userId;
        SQLiteDatabase sqLiteDatabase = sqlLiteManager.getReadableDatabase();
        User user = null;

        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                user = new User(userId, username, password);
            }
        }

        return user;
    }
}
