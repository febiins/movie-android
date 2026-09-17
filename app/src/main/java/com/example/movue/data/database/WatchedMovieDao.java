package com.example.movue.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.movue.model.Movie;
import com.example.movue.model.WatchedMovie;
import java.util.List;

@Dao
public interface WatchedMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void markAsWatched(WatchedMovie watchedMovie);

    @Query("SELECT movies.* FROM movies INNER JOIN watched_movies ON movies.id = watched_movies.movieId WHERE watched_movies.userId = :userId ORDER BY watched_movies.watchedDate DESC")
    LiveData<List<Movie>> getWatchedMovies(int userId);

    @Query("SELECT EXISTS(SELECT 1 FROM watched_movies WHERE userId = :userId AND movieId = :movieId)")
    LiveData<Boolean> isWatched(int userId, int movieId);
}