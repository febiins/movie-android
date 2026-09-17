package com.example.movue.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.movue.model.Movie;
import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<Movie> movies);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :movieId")
    LiveData<Movie> getMovieById(int movieId);

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :searchQuery || '%'")
    LiveData<List<Movie>> searchMovies(String searchQuery);
}