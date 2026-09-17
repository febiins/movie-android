package com.example.movue.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private String posterPath;
    private String releaseYear;
    private String genre;
    private String runtime;

    public Movie(int id, String title, String description, String posterPath, String releaseYear, String genre, String runtime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.posterPath = posterPath;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.runtime = runtime;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getReleaseYear() { return releaseYear; }
    public void setReleaseYear(String releaseYear) { this.releaseYear = releaseYear; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getRuntime() { return runtime; }
    public void setRuntime(String runtime) { this.runtime = runtime; }
}