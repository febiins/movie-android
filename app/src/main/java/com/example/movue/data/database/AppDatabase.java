package com.example.movue.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.movue.model.Movie;
import com.example.movue.model.Review;
import com.example.movue.model.WatchedMovie;
import com.example.movue.model.Watchlist;

@Database(entities = {Movie.class, Review.class, Watchlist.class, WatchedMovie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract MovieDao movieDao();
    public abstract ReviewDao reviewDao();
    public abstract WatchlistDao watchlistDao();
    public abstract WatchedMovieDao watchedMovieDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "movue_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}