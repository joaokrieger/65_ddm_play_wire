package com.ddm.playwire.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ddm.playwire.model.Review;
import com.ddm.playwire.model.ReviewComment;
import com.ddm.playwire.model.User;

import java.util.ArrayList;
import java.util.List;

public class ReviewCommentDao extends SQLiteOpenHelper {

    private Context context;
    private static final String TABLE_NAME = "review_comment";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_REVIEW = "id_review";
    private static final String COLUMN_USER = "id_user";
    private static final String COLUMN_COMMENT = "comment";

    public ReviewCommentDao(@Nullable Context context) {
        super(context, SQLiteManager.DATABASE_NAME, null, SQLiteManager.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_REVIEW + " INTEGER, "
                + COLUMN_USER + " INTEGER, "
                + COLUMN_COMMENT + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_REVIEW + ") REFERENCES review(_id), "
                + "FOREIGN KEY (" + COLUMN_USER + ") REFERENCES user(_id))";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public List<ReviewComment> listAllByReviewId(int reviewId){

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_REVIEW + " = " + reviewId + " ORDER BY 1 DESC";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        List<ReviewComment> reviewComments = new ArrayList<>();

        if(sqLiteDatabase != null) {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while (cursor.moveToNext()) {

                int id = Integer.parseInt(cursor.getString(0));
                String comment = cursor.getString(4);

                ReviewDao reviewDao = new ReviewDao(this.context);
                Review review = reviewDao.loadByReviewId(Integer.parseInt(cursor.getString(2)));

                UserDao userDao = new UserDao(this.context);
                User user = userDao.loadByUserId(Integer.parseInt(cursor.getString(3)));

                ReviewComment reviewComment = new ReviewComment(id, review, user, comment);
                reviewComments.add(reviewComment);
            }
        }

        return reviewComments;
    }
}
