package com.example.movue.model;

public class WatchedMovie {
    private int movieId;
    private String watchedDate;
    private float rating;
    private String review;

    public WatchedMovie(int movieId, String watchedDate, float rating, String review) {
        this.movieId = movieId;
        this.watchedDate = watchedDate;
        this.rating = rating;
        this.review = review;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}