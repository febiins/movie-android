package com.example.movue.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.movue.model.Review;
import java.util.List;

@Dao
public interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReview(Review review);

    @Query("SELECT * FROM reviews WHERE movieId = :movieId ORDER BY createdAt DESC")
    LiveData<List<Review>> getReviewsForMovie(int movieId);

    @Query("SELECT * FROM reviews WHERE userId = :userId ORDER BY createdAt DESC")
    LiveData<List<Review>> getReviewsByUser(int userId);

    @Delete
    void deleteReview(Review review);
}