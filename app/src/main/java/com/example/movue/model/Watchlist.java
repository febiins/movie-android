package com.example.movue.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "watchlist")
public class Watchlist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int movieId;
    private String addedAt;

    public Watchlist(int userId, int movieId, String addedAt) {
        this.userId = userId;
        this.movieId = movieId;
        this.addedAt = addedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public String getAddedAt() { return addedAt; }
    public void setAddedAt(String addedAt) { this.addedAt = addedAt; }
}