package com.example.movue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movue.model.Movie;
import com.example.movue.model.Review;
import com.example.movue.model.User;
import com.example.movue.model.WatchedMovie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movue.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_MOVIES = "movies";
    private static final String TABLE_WATCHLIST = "watchlist";
    private static final String TABLE_WATCHED_MOVIES = "watched_movies";
    private static final String TABLE_REVIEWS = "reviews";

    // Users Table Columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Movies Table Columns
    private static final String COLUMN_MOVIE_ID = "id";
    private static final String COLUMN_MOVIE_TITLE = "title";
    private static final String COLUMN_MOVIE_SYNOPSIS = "synopsis";
    private static final String COLUMN_MOVIE_YEAR = "year";
    private static final String COLUMN_MOVIE_GENRE = "genre";
    private static final String COLUMN_MOVIE_RUNTIME = "runtime";
    private static final String COLUMN_MOVIE_RATING = "rating";
    private static final String COLUMN_MOVIE_POSTER = "poster";

    // Watchlist Table Columns
    private static final String COLUMN_WATCHLIST_ID = "id";
    private static final String COLUMN_WATCHLIST_USER_ID = "user_id";
    private static final String COLUMN_WATCHLIST_MOVIE_ID = "movie_id";

    // Watched Movies Table Columns
    private static final String COLUMN_WATCHED_ID = "id";
    private static final String COLUMN_WATCHED_USER_ID = "user_id";
    private static final String COLUMN_WATCHED_MOVIE_ID = "movie_id";
    private static final String COLUMN_WATCHED_DATE = "watched_date";
    private static final String COLUMN_WATCHED_RATING = "rating";

    // Reviews Table Columns
    private static final String COLUMN_REVIEW_ID = "id";
    private static final String COLUMN_REVIEW_USER_ID = "user_id";
    private static final String COLUMN_REVIEW_MOVIE_ID = "movie_id";
    private static final String COLUMN_REVIEW_RATING = "rating";
    private static final String COLUMN_REVIEW_TEXT = "review_text";
    private static final String COLUMN_REVIEW_DATE = "review_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_USERNAME + " TEXT NOT NULL UNIQUE, " +
                COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL" +
                ");";

        // Create Movies Table
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + " (" +
                COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                COLUMN_MOVIE_SYNOPSIS + " TEXT, " +
                COLUMN_MOVIE_YEAR + " INTEGER, " +
                COLUMN_MOVIE_GENRE + " TEXT, " +
                COLUMN_MOVIE_RUNTIME + " INTEGER, " +
                COLUMN_MOVIE_RATING + " REAL, " +
                COLUMN_MOVIE_POSTER + " INTEGER" +
                ");";

        // Create Watchlist Table
        String CREATE_WATCHLIST_TABLE = "CREATE TABLE " + TABLE_WATCHLIST + " (" +
                COLUMN_WATCHLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WATCHLIST_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_WATCHLIST_MOVIE_ID + " INTEGER NOT NULL, " +
                "UNIQUE(" + COLUMN_WATCHLIST_USER_ID + ", " + COLUMN_WATCHLIST_MOVIE_ID + "), " +
                "FOREIGN KEY(" + COLUMN_WATCHLIST_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + COLUMN_WATCHLIST_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_MOVIE_ID + ") ON DELETE CASCADE" +
                ");";

        // Create Watched Movies Table
        String CREATE_WATCHED_MOVIES_TABLE = "CREATE TABLE " + TABLE_WATCHED_MOVIES + " (" +
                COLUMN_WATCHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WATCHED_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_WATCHED_MOVIE_ID + " INTEGER NOT NULL, " +
                COLUMN_WATCHED_DATE + " TEXT, " +
                COLUMN_WATCHED_RATING + " REAL, " +
                "UNIQUE(" + COLUMN_WATCHED_USER_ID + ", " + COLUMN_WATCHED_MOVIE_ID + "), " +
                "FOREIGN KEY(" + COLUMN_WATCHED_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + COLUMN_WATCHED_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_MOVIE_ID + ") ON DELETE CASCADE" +
                ");";

        // Create Reviews Table
        String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS + " (" +
                COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REVIEW_USER_ID + " INTEGER NOT NULL, " +
                COLUMN_REVIEW_MOVIE_ID + " INTEGER NOT NULL, " +
                COLUMN_REVIEW_RATING + " REAL, " +
                COLUMN_REVIEW_TEXT + " TEXT, " +
                COLUMN_REVIEW_DATE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_REVIEW_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + COLUMN_REVIEW_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_MOVIE_ID + ") ON DELETE CASCADE" +
                ");";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_MOVIES_TABLE);
        db.execSQL(CREATE_WATCHLIST_TABLE);
        db.execSQL(CREATE_WATCHED_MOVIES_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);

        // Populate initial movie data
        populateInitialMovies(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHED_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public int getMoviesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_MOVIES, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    public void insertInitialMovies(List<Movie> movies) {
        if (movies == null || movies.isEmpty()) return;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Movie movie : movies) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_MOVIE_ID, movie.getId());
                values.put(COLUMN_MOVIE_TITLE, movie.getTitle());
                values.put(COLUMN_MOVIE_SYNOPSIS, movie.getDescription());
                values.put(COLUMN_MOVIE_YEAR, movie.getReleaseYear());
                values.put(COLUMN_MOVIE_GENRE, movie.getGenre());
                values.put(COLUMN_MOVIE_RUNTIME, parseRuntimeInteger(movie.getRuntime()));
                values.put(COLUMN_MOVIE_RATING, movie.getRating());
                values.put(COLUMN_MOVIE_POSTER, movie.getPosterResId());

                db.insertWithOnConflict(TABLE_MOVIES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void initializeMovies() {
        if (getMoviesCount() == 0) {
            insertInitialMovies(MovieData.getSampleMovies());
        }
    }

    public void populateInitialMovies(SQLiteDatabase db) {
        List<Movie> sampleMovies = MovieData.getSampleMovies();
        for (Movie movie : sampleMovies) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MOVIE_ID, movie.getId());
            values.put(COLUMN_MOVIE_TITLE, movie.getTitle());
            values.put(COLUMN_MOVIE_SYNOPSIS, movie.getDescription());
            values.put(COLUMN_MOVIE_YEAR, movie.getReleaseYear());
            values.put(COLUMN_MOVIE_GENRE, movie.getGenre());
            values.put(COLUMN_MOVIE_RUNTIME, parseRuntimeInteger(movie.getRuntime()));
            values.put(COLUMN_MOVIE_RATING, movie.getRating());
            values.put(COLUMN_MOVIE_POSTER, movie.getPosterResId());

            db.insertWithOnConflict(TABLE_MOVIES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    private int parseRuntimeInteger(String runtimeStr) {
        if (runtimeStr == null) return 0;
        try {
            String digits = runtimeStr.replaceAll("[^0-9]", "");
            return digits.isEmpty() ? 0 : Integer.parseInt(digits);
        } catch (Exception e) {
            return 0;
        }
    }

    // ==========================================
    // USER METHODS
    // ==========================================

    public boolean checkUserExists(String username, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID},
                    COLUMN_USER_USERNAME + " = ? OR " + COLUMN_USER_EMAIL + " = ?",
                    new String[]{username, email}, null, null, null);
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public long insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, username);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        return db.insert(TABLE_USERS, null, values);
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, null,
                    COLUMN_USER_EMAIL + " = ?",
                    new String[]{email}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));

                return new User(id, username, userEmail);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, null,
                    COLUMN_USER_USERNAME + " = ?",
                    new String[]{username}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String uName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));

                return new User(id, uName, userEmail);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public User authenticateUser(String emailOrUsername, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, null,
                    "(" + COLUMN_USER_EMAIL + " = ? OR " + COLUMN_USER_USERNAME + " = ?) AND " + COLUMN_USER_PASSWORD + " = ?",
                    new String[]{emailOrUsername, emailOrUsername, password}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL));

                return new User(id, username, email);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    // ==========================================
    // MOVIE METHODS
    // ==========================================

    public long insertMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movie.getId());
        values.put(COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(COLUMN_MOVIE_SYNOPSIS, movie.getDescription());
        values.put(COLUMN_MOVIE_YEAR, movie.getReleaseYear());
        values.put(COLUMN_MOVIE_GENRE, movie.getGenre());
        values.put(COLUMN_MOVIE_RUNTIME, parseRuntimeInteger(movie.getRuntime()));
        values.put(COLUMN_MOVIE_RATING, movie.getRating());
        values.put(COLUMN_MOVIE_POSTER, movie.getPosterResId());

        return db.insertWithOnConflict(TABLE_MOVIES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Movie getMovieById(int movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_MOVIES, null,
                    COLUMN_MOVIE_ID + " = ?",
                    new String[]{String.valueOf(movieId)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                return cursorToMovie(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_MOVIES, null, null, null, null, null, COLUMN_MOVIE_TITLE + " ASC");
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    movieList.add(cursorToMovie(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return movieList;
    }

    public List<Movie> searchMovies(String query) {
        List<Movie> movieList = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return movieList;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String searchPattern = "%" + query.trim() + "%";

        try {
            cursor = db.query(TABLE_MOVIES, null,
                    COLUMN_MOVIE_TITLE + " LIKE ? OR " + COLUMN_MOVIE_GENRE + " LIKE ? OR CAST(" + COLUMN_MOVIE_YEAR + " AS TEXT) LIKE ?",
                    new String[]{searchPattern, searchPattern, searchPattern}, null, null, COLUMN_MOVIE_TITLE + " ASC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    movieList.add(cursorToMovie(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return movieList;
    }

    private Movie cursorToMovie(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_TITLE));
        String synopsis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_SYNOPSIS));
        int year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_YEAR));
        String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_GENRE));
        int runtimeInt = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_RUNTIME));
        double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_RATING));
        int poster = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_POSTER));

        String runtimeStr = runtimeInt + " min";
        return new Movie(id, title, synopsis, year, genre, runtimeStr, rating, poster);
    }

    // ==========================================
    // WATCHLIST METHODS
    // ==========================================

    public boolean addToWatchlist(int userId, int movieId) {
        if (userId <= 0 || movieId <= 0) return false;
        if (isInWatchlist(userId, movieId)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WATCHLIST_USER_ID, userId);
        values.put(COLUMN_WATCHLIST_MOVIE_ID, movieId);

        long result = db.insertWithOnConflict(TABLE_WATCHLIST, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        return result != -1;
    }

    public boolean removeFromWatchlist(int userId, int movieId) {
        if (userId <= 0 || movieId <= 0) return false;
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_WATCHLIST,
                COLUMN_WATCHLIST_USER_ID + " = ? AND " + COLUMN_WATCHLIST_MOVIE_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(movieId)});
        return rowsDeleted > 0;
    }

    public boolean isInWatchlist(int userId, int movieId) {
        if (userId <= 0 || movieId <= 0) return false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_WATCHLIST, new String[]{COLUMN_WATCHLIST_ID},
                    COLUMN_WATCHLIST_USER_ID + " = ? AND " + COLUMN_WATCHLIST_MOVIE_ID + " = ?",
                    new String[]{String.valueOf(userId), String.valueOf(movieId)}, null, null, null);
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<Movie> getWatchlistMovies(int userId) {
        List<Movie> movieList = new ArrayList<>();
        if (userId <= 0) return movieList;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT m.* FROM " + TABLE_MOVIES + " m " +
                "INNER JOIN " + TABLE_WATCHLIST + " w ON m." + COLUMN_MOVIE_ID + " = w." + COLUMN_WATCHLIST_MOVIE_ID + " " +
                "WHERE w." + COLUMN_WATCHLIST_USER_ID + " = ? " +
                "ORDER BY w." + COLUMN_WATCHLIST_ID + " DESC";

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    movieList.add(cursorToMovie(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return movieList;
    }

    // ==========================================
    // WATCHED METHODS
    // ==========================================

    public long markAsWatched(int userId, int movieId, String watchedDate, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WATCHED_USER_ID, userId);
        values.put(COLUMN_WATCHED_MOVIE_ID, movieId);
        values.put(COLUMN_WATCHED_DATE, watchedDate);
        values.put(COLUMN_WATCHED_RATING, rating);

        return db.insertWithOnConflict(TABLE_WATCHED_MOVIES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public boolean isWatched(int userId, int movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_WATCHED_MOVIES, new String[]{COLUMN_WATCHED_ID},
                    COLUMN_WATCHED_USER_ID + " = ? AND " + COLUMN_WATCHED_MOVIE_ID + " = ?",
                    new String[]{String.valueOf(userId), String.valueOf(movieId)}, null, null, null);
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<WatchedMovie> getWatchedMovies(int userId) {
        List<WatchedMovie> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT wm." + COLUMN_WATCHED_MOVIE_ID + ", wm." + COLUMN_WATCHED_DATE + ", wm." + COLUMN_WATCHED_RATING + ", " +
                "r." + COLUMN_REVIEW_TEXT + " " +
                "FROM " + TABLE_WATCHED_MOVIES + " wm " +
                "LEFT JOIN " + TABLE_REVIEWS + " r ON wm." + COLUMN_WATCHED_USER_ID + " = r." + COLUMN_REVIEW_USER_ID + " " +
                "AND wm." + COLUMN_WATCHED_MOVIE_ID + " = r." + COLUMN_REVIEW_MOVIE_ID + " " +
                "WHERE wm." + COLUMN_WATCHED_USER_ID + " = ? " +
                "ORDER BY wm." + COLUMN_WATCHED_ID + " DESC";

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int movieId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WATCHED_MOVIE_ID));
                    String watchedDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WATCHED_DATE));
                    float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_WATCHED_RATING));
                    String reviewText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_TEXT));

                    list.add(new WatchedMovie(movieId, watchedDate, rating, reviewText != null ? reviewText : ""));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    // ==========================================
    // REVIEWS METHODS
    // ==========================================

    public long addReview(int userId, int movieId, float rating, String reviewText, String reviewDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_USER_ID, userId);
        values.put(COLUMN_REVIEW_MOVIE_ID, movieId);
        values.put(COLUMN_REVIEW_RATING, rating);
        values.put(COLUMN_REVIEW_TEXT, reviewText);
        values.put(COLUMN_REVIEW_DATE, reviewDate);

        return db.insert(TABLE_REVIEWS, null, values);
    }

    public List<Review> getReviewsForMovie(int movieId) {
        List<Review> reviewList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT r.*, u." + COLUMN_USER_USERNAME + " FROM " + TABLE_REVIEWS + " r " +
                "LEFT JOIN " + TABLE_USERS + " u ON r." + COLUMN_REVIEW_USER_ID + " = u." + COLUMN_USER_ID + " " +
                "WHERE r." + COLUMN_REVIEW_MOVIE_ID + " = ? " +
                "ORDER BY r." + COLUMN_REVIEW_ID + " DESC";

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(movieId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
                    int mId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_MOVIE_ID));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME));
                    float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RATING));
                    String reviewText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_TEXT));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_DATE));

                    if (username == null) {
                        username = "User";
                    }

                    reviewList.add(new Review(id, mId, username, rating, reviewText, date));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reviewList;
    }

    public List<Review> getReviewsByUser(int userId) {
        List<Review> reviewList = new ArrayList<>();
        if (userId <= 0) return reviewList;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT r.*, u." + COLUMN_USER_USERNAME + " FROM " + TABLE_REVIEWS + " r " +
                "LEFT JOIN " + TABLE_USERS + " u ON r." + COLUMN_REVIEW_USER_ID + " = u." + COLUMN_USER_ID + " " +
                "WHERE r." + COLUMN_REVIEW_USER_ID + " = ? " +
                "ORDER BY r." + COLUMN_REVIEW_ID + " DESC";

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
                    int mId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_MOVIE_ID));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_USERNAME));
                    float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_RATING));
                    String reviewText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_TEXT));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_DATE));

                    if (username == null) {
                        username = "User";
                    }

                    reviewList.add(new Review(id, mId, username, rating, reviewText, date));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reviewList;
    }

    public int getReviewCount(int userId) {
        if (userId <= 0) return 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_REVIEWS + " WHERE " + COLUMN_REVIEW_USER_ID + " = ?",
                    new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }
}