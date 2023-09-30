package com.ddm.playwire.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ddm.playwire.models.Review;
import com.ddm.playwire.models.User;

import java.util.ArrayList;
import java.util.List;

public class ReviewDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String TABLE_NAME = "review";

    private static final String COLUMN_ID = "id";
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

    public void insertReview(String gameTitle, String reviewDescription, String feedback, int userId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAME_TITLE, gameTitle);
        contentValues.put(COLUMN_REVIEW_DESCRIPTION, reviewDescription);
        contentValues.put(COLUMN_FEEDBACK, feedback);
        contentValues.put(COLUMN_USER_ID, userId);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(context, result);
    }

    public List<Review> listReview(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        List<Review> reviews = new ArrayList<Review>();

        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                User user = new User(1, "JEK", "123");
                int id = Integer.parseInt(cursor.getString(0));
                String gameTitle = cursor.getString(1);
                String reviewDescription = cursor.getString(2);
                String feedback = cursor.getString(3);

                Review review = new Review(id, gameTitle, reviewDescription, feedback, user);
                reviews.add(review);
            }
        }

        return reviews;
    }
}
