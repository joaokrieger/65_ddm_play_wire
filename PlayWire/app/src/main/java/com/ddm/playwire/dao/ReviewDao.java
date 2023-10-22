package com.ddm.playwire.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.User;

import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends SQLiteOpenHelper {

    private Context context;

    private static final String TABLE_NAME = "review";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_GAME_TITLE = "game_title";
    private static final String COLUMN_REVIEW_DESCRIPTION = "review_description";
    private static final String COLUMN_FEEDBACK = "feedback";
    private static final String COLUMN_USER_ID = "user_id";

    public ReviewDao(@Nullable Context context) {
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

    public void insert(Review review){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GAME_TITLE, review.getGameTitle());
        contentValues.put(COLUMN_REVIEW_DESCRIPTION, review.getReviewDescription());
        contentValues.put(COLUMN_FEEDBACK, review.getFeedback());
        contentValues.put(COLUMN_USER_ID, review.getUser().getUserId());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        SQLiteManager.checkExecSql(context, result);
    }

    public void delete(Review review) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "_id = ?";
        String[] whereArgs = { String.valueOf(review.getReviewId()) };

        long result = sqLiteDatabase.delete(TABLE_NAME, query, whereArgs);
        SQLiteManager.checkExecSql(context, result);
    }

    public List<Review> listAll(){
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY 1 DESC";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        List<Review> reviews = new ArrayList<>();

        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                int id = Integer.parseInt(cursor.getString(0));
                String gameTitle = cursor.getString(1);
                String reviewDescription = cursor.getString(2);
                String feedback = cursor.getString(3);

                UserDao userDao = new UserDao(this.context);
                User user = userDao.loadByUserId(Integer.parseInt(cursor.getString(4)));

                Review review = new Review(id, gameTitle, reviewDescription, feedback, user);
                reviews.add(review);
            }
        }

        return reviews;
    }

    public List<String[]> listRank(){

        String query = "SELECT " + COLUMN_GAME_TITLE + ", COUNT(*) AS review_count, AVG(CASE " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Excelente' THEN 5 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Bom' THEN 4 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Regular' THEN 3 " +
                "  WHEN " + COLUMN_FEEDBACK + " = 'Insatisfatório' THEN 2 " +
                "  ELSE 1 END) AS avg_feedback " +
                " FROM " + TABLE_NAME +
                " GROUP BY " + COLUMN_GAME_TITLE +
                " ORDER BY avg_feedback DESC " +
                " LIMIT 5";

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        List<String[]> reviews = new ArrayList<>();
        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                String gameTitle = cursor.getString(0);
                int reviewCount = cursor.getInt(1);
                int feedbackAvg = cursor.getInt(2);

                String[] reviewData = {gameTitle, String.valueOf(reviewCount), String.valueOf(feedbackAvg)};
                reviews.add(reviewData);
            }
        }

        return reviews;
    }

    public Review loadById(int reviewId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + reviewId;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Review review = null;

        if(sqLiteDatabase != null){
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while(cursor.moveToNext()){

                int id = Integer.parseInt(cursor.getString(0));
                String gameTitle = cursor.getString(1);
                String reviewDescription = cursor.getString(2);
                String feedback = cursor.getString(3);

                UserDao userDao = new UserDao(this.context);
                User user = userDao.loadByUserId(Integer.parseInt(cursor.getString(4)));

                review = new Review(id, gameTitle, reviewDescription, feedback, user);
            }
        }

        return review;
    }


}
