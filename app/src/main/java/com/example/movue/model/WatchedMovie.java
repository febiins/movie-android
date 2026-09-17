package com.example.movue.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "watched_movies")
public class WatchedMovie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int movieId;
    private String watchedDate;
    private float rating;
    private int reviewId;

    public WatchedMovie(int userId, int movieId, String watchedDate, float rating, int reviewId) {
        this.userId = userId;
        this.movieId = movieId;
        this.watchedDate = watchedDate;
        this.rating = rating;
        this.reviewId = reviewId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public String getWatchedDate() { return watchedDate; }
    public void setWatchedDate(String watchedDate) { this.watchedDate = watchedDate; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }
}