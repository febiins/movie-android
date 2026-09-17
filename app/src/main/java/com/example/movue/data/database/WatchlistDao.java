package com.example.movue.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.movue.model.Movie;
import com.example.movue.model.Watchlist;
import java.util.List;

@Dao
public interface WatchlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToWatchlist(Watchlist watchlist);

    @Delete
    void removeFromWatchlist(Watchlist watchlist);

    @Query("SELECT movies.* FROM movies INNER JOIN watchlist ON movies.id = watchlist.movieId WHERE watchlist.userId = :userId")
    LiveData<List<Movie>> getWatchlist(int userId);

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE userId = :userId AND movieId = :movieId)")
    LiveData<Boolean> isInWatchlist(int userId, int movieId);
}