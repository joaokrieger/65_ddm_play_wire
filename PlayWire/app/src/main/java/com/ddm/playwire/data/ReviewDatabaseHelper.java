package com.ddm.playwire.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ReviewDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String TABLE_NAME = "review";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GAME_TITLE = "game_title";
    private static final String COLUMN_REVIEW_DESCRIPTION = "review_description";
    private static final String COLUMN_FEEDBACK = "feedback";
    private static final String COLUMN_USER_ID = "user_id";

    public ReviewDatabaseHelper(@Nullable Context context) {
        super(context, SQLiteManager.DATABASE_NAME, null, SQLiteManager.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_GAME_TITLE + " TEXT, "
            + COLUMN_REVIEW_DESCRIPTION + " TEXT, "
            + COLUMN_FEEDBACK + " TEXT, "
            + COLUMN_USER_ID + " INTEGER)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void addReview(String gameTitle, String reviewDescription, String feedback, int userId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAME_TITLE, gameTitle);
        contentValues.put(COLUMN_REVIEW_DESCRIPTION, reviewDescription);
        contentValues.put(COLUMN_FEEDBACK, feedback);
        contentValues.put(COLUMN_USER_ID, userId);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(context, result);
    }
}
