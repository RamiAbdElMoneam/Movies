package com.example.home.movies.movieDatabaseSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.home.movies.movieDatabaseSqlite.MovieContract.MovieEntry;

/**
 * Created by Windows 7 on 1/6/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "movie.db";

    //----------------------------------------------------------------------------------------------
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +

                MovieEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY," +

                MovieEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " +

                MovieEntry.COLUMN_MOVIE_IMAGE + " TEXT NOT NULL, " +

                MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL," +

                MovieEntry.COLUMN_MOVIE_RATE + " TEXT NOT NULL, " +

                MovieEntry.COLUMN_MOVIE_DATE + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }

    //**********************************************************************************************
    public boolean insertData(String movieId, String moviePoster, String movieName, String movieOverview,
                              String movieRate, String movieDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_MOVIE_ID, movieId);
        contentValues.put(MovieEntry.COLUMN_MOVIE_IMAGE, moviePoster);
        contentValues.put(MovieEntry.COLUMN_MOVIE_NAME, movieName);
        contentValues.put(MovieEntry.COLUMN_MOVIE_OVERVIEW, movieOverview);
        contentValues.put(MovieEntry.COLUMN_MOVIE_RATE, movieRate);
        contentValues.put(MovieEntry.COLUMN_MOVIE_DATE, movieDate);
        // insert return -1 if mission failed
        long result = db.insert(MovieEntry.TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //----------------------------------------------------------------------------------------------
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MovieEntry.TABLE_NAME, null);
        return res;
    }
    //----------------------------------------------------------------------------------------------

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MovieEntry.TABLE_NAME, "movie_id = ?", new String[]{id});
    }

    //**********************************************************************************************
    public Integer checkData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cusror = db.rawQuery("SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE " + MovieEntry.COLUMN_MOVIE_ID + " = " + id, null);
        if (cusror.getCount() == 0) {
            return 0;
        } else {
            return 1;
        }
    }
    //**********************************************************************************************
}
