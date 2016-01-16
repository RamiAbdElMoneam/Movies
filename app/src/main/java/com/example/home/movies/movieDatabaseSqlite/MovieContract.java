package com.example.home.movies.movieDatabaseSqlite;

import android.provider.BaseColumns;

/**
 * Created by Windows 7 on 1/6/2016.
 */
public class MovieContract {

    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_MOVIE_IMAGE = "movie_poster";

        public static final String COLUMN_MOVIE_NAME = "movie_name";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

        public static final String COLUMN_MOVIE_RATE = "movie_rate";

        public static final String COLUMN_MOVIE_DATE = "movie_date";
    }

}
