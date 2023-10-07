package com.ddm.playwire.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ddm.playwire.models.User;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String TABLE_NAME = "user";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, SQLiteManager.DATABASE_NAME, null, SQLiteManager.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public Long insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(context, result);

        return result;
    }

    public User loadUserByCredentials(String username, String password){

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = '"+ username + "' AND " + COLUMN_PASSWORD + " = '" + password + "'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
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

    public User loadByUserId(int userId){

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = "+ userId;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
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
