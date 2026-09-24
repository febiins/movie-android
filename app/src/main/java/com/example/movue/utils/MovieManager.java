package com.example.movue.utils;

import com.example.movue.data.MovieData;
import com.example.movue.model.Movie;
import com.example.movue.model.Review;
import com.example.movue.model.WatchedMovie;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    private static MovieManager instance;

    private final List<Movie> allMovies;
    private final List<Integer> watchlistMovieIds;
    private final List<WatchedMovie> watchedMovies;
    private final List<Review> reviews;

    private MovieManager() {
        allMovies = new ArrayList<>(MovieData.getSampleMovies());
        watchlistMovieIds = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        reviews = new ArrayList<>();

        // Add some initial sample activity for demo
        if (!allMovies.isEmpty()) {
            watchlistMovieIds.add(allMovies.get(0).getId());
            watchlistMovieIds.add(allMovies.get(1).getId());

            WatchedMovie sampleWatched = new WatchedMovie(allMovies.get(0).getId(), "15 Sep 2026", 5.0f, "Mind-bending masterpiece!");
            watchedMovies.add(sampleWatched);

            Review sampleReview = new Review(1, allMovies.get(0).getId(), "User", 5.0f, "Mind-bending masterpiece! Loved Nolan's direction.", "15 Sep 2026");
            reviews.add(sampleReview);
        }
    }

    public static synchronized MovieManager getInstance() {
        if (instance == null) {
            instance = new MovieManager();
        }
        return instance;
    }

    public List<Movie> getAllMovies() {
        return allMovies;
    }

    public Movie getMovieById(int id) {
        for (Movie movie : allMovies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    public List<Movie> searchMovies(String query) {
        List<Movie> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            return results;
        }

        String lowerQuery = query.toLowerCase().trim();
        for (Movie movie : allMovies) {
            if (movie.getTitle().toLowerCase().contains(lowerQuery) ||
                movie.getGenre().toLowerCase().contains(lowerQuery) ||
                String.valueOf(movie.getReleaseYear()).contains(lowerQuery)) {
                results.add(movie);
            }
        }
        return results;
    }

    // Watchlist operations
    public void addToWatchlist(int movieId) {
        if (!watchlistMovieIds.contains(movieId)) {
            watchlistMovieIds.add(movieId);
        }
    }

    public void removeFromWatchlist(int movieId) {
        watchlistMovieIds.remove(Integer.valueOf(movieId));
    }

    public boolean isInWatchlist(int movieId) {
        return watchlistMovieIds.contains(movieId);
    }

    public List<Movie> getWatchlistMovies() {
        List<Movie> list = new ArrayList<>();
        for (int id : watchlistMovieIds) {
            Movie m = getMovieById(id);
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

    // Watched movies operations
    public void markAsWatched(int movieId, String date, float rating, String reviewText) {
        if (!isWatched(movieId)) {
            WatchedMovie watched = new WatchedMovie(movieId, date, rating, reviewText);
            watchedMovies.add(watched);
        }
        if (reviewText != null && !reviewText.trim().isEmpty()) {
            addReview(new Review(reviews.size() + 1, movieId, "User", rating, reviewText, date));
        }
    }

    public boolean isWatched(int movieId) {
        for (WatchedMovie wm : watchedMovies) {
            if (wm.getMovieId() == movieId) {
                return true;
            }
        }
        return false;
    }

    public List<WatchedMovie> getWatchedMovies() {
        return watchedMovies;
    }

    public List<Movie> getWatchedMoviesList() {
        List<Movie> list = new ArrayList<>();
        for (WatchedMovie wm : watchedMovies) {
            Movie m = getMovieById(wm.getMovieId());
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

    // Reviews operations
    public void addReview(Review review) {
        reviews.add(review);
    }

    public List<Review> getReviewsForMovie(int movieId) {
        List<Review> movieReviews = new ArrayList<>();
        for (Review r : reviews) {
            if (r.getMovieId() == movieId) {
                movieReviews.add(r);
            }
        }
        return movieReviews;
    }

    public List<Review> getAllReviews() {
        return reviews;
    }

    // Statistics
    public int getWatchedCount() {
        return watchedMovies.size();
    }

    public int getReviewsCount() {
        return reviews.size();
    }

    public int getWatchlistCount() {
        return watchlistMovieIds.size();
    }

    public float getAverageRating() {
        if (reviews.isEmpty()) return 0.0f;
        float total = 0.0f;
        for (Review r : reviews) {
            total += r.getRating();
        }
        return total / reviews.size();
    }
}