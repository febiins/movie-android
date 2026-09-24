package com.example.movue.model;

public class Review {
    private int id;
    private int movieId;
    private String username;
    private float rating;
    private String reviewText;
    private String date;

    public Review(int id, int movieId, String username, float rating, String reviewText, String date) {
        this.id = id;
        this.movieId = movieId;
        this.username = username;
        this.rating = rating;
        this.reviewText = reviewText;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}