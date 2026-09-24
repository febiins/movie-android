package com.example.movue.model;

public class Movie {
    private int id;
    private String title;
    private String description;
    private int releaseYear;
    private String genre;
    private String runtime;
    private double rating;
    private int posterResId;

    public Movie(int id, String title, String description, int releaseYear, String genre, String runtime, double rating, int posterResId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.runtime = runtime;
        this.rating = rating;
        this.posterResId = posterResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getPosterResId() {
        return posterResId;
    }

    public void setPosterResId(int posterResId) {
        this.posterResId = posterResId;
    }
}