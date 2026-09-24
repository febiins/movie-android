package com.example.movue.utils;

import android.content.Context;

import com.example.movue.data.DatabaseHelper;
import com.example.movue.model.Movie;
import com.example.movue.model.Review;
import com.example.movue.model.User;
import com.example.movue.model.WatchedMovie;

import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    private static MovieManager instance;

    private Context context;
    private DatabaseHelper dbHelper;

    private MovieManager(Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();
            this.dbHelper = new DatabaseHelper(this.context);
            this.dbHelper.initializeMovies();
        }
    }

    public static synchronized MovieManager getInstance(Context context) {
        if (instance == null) {
            instance = new MovieManager(context);
        } else if (context != null) {
            instance.context = context.getApplicationContext();
            if (instance.dbHelper == null) {
                instance.dbHelper = new DatabaseHelper(instance.context);
                instance.dbHelper.initializeMovies();
            }
        }
        return instance;
    }

    public static synchronized MovieManager getInstance() {
        if (instance == null) {
            instance = new MovieManager(null);
        }
        return instance;
    }

    private int getCurrentUserId() {
        if (context != null) {
            int userId = PreferenceManager.getInstance(context).getUserId();
            if (userId <= 0) {
                String email = PreferenceManager.getInstance(context).getEmail();
                if (!email.isEmpty() && dbHelper != null) {
                    User user = dbHelper.getUserByEmail(email);
                    if (user != null) {
                        userId = user.getId();
                        PreferenceManager.getInstance(context).saveUserId(userId);
                    }
                }
            }
            return userId;
        }
        return -1;
    }

    public List<Movie> getAllMovies() {
        if (dbHelper != null) {
            return dbHelper.getAllMovies();
        }
        return new ArrayList<>();
    }

    public Movie getMovieById(int id) {
        if (dbHelper != null) {
            return dbHelper.getMovieById(id);
        }
        return null;
    }

    public List<Movie> searchMovies(String query) {
        if (dbHelper != null) {
            return dbHelper.searchMovies(query);
        }
        return new ArrayList<>();
    }

    // Watchlist operations (SQLite backed)
    public boolean addToWatchlist(int movieId) {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.addToWatchlist(userId, movieId);
        }
        return false;
    }

    public boolean removeFromWatchlist(int movieId) {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.removeFromWatchlist(userId, movieId);
        }
        return false;
    }

    public boolean isInWatchlist(int movieId) {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.isInWatchlist(userId, movieId);
        }
        return false;
    }

    public List<Movie> getWatchlistMovies() {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.getWatchlistMovies(userId);
        }
        return new ArrayList<>();
    }

    public int getWatchlistCount() {
        return getWatchlistMovies().size();
    }

    // Watched movies operations (SQLite backed)
    public boolean markAsWatched(int movieId, String date, float rating, String reviewText) {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            long result = dbHelper.markAsWatched(userId, movieId, date, rating);
            if (reviewText != null && !reviewText.trim().isEmpty()) {
                dbHelper.addReview(userId, movieId, rating, reviewText, date);
            }
            return result != -1;
        }
        return false;
    }

    public boolean isWatched(int movieId) {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.isWatched(userId, movieId);
        }
        return false;
    }

    public List<WatchedMovie> getWatchedMovies() {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.getWatchedMovies(userId);
        }
        return new ArrayList<>();
    }

    public List<Movie> getWatchedMoviesList() {
        List<Movie> list = new ArrayList<>();
        List<WatchedMovie> watchedList = getWatchedMovies();
        for (WatchedMovie wm : watchedList) {
            Movie m = getMovieById(wm.getMovieId());
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

    public int getWatchedCount() {
        return getWatchedMovies().size();
    }

    // Reviews operations (SQLite backed)
    public long addReview(int movieId, float rating, String reviewText, String date) {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            dbHelper.markAsWatched(userId, movieId, date, rating);
            return dbHelper.addReview(userId, movieId, rating, reviewText, date);
        }
        return -1;
    }

    public void addReview(Review review) {
        if (review != null) {
            addReview(review.getMovieId(), review.getRating(), review.getReviewText(), review.getDate());
        }
    }

    public List<Review> getReviewsForMovie(int movieId) {
        if (dbHelper != null) {
            return dbHelper.getReviewsForMovie(movieId);
        }
        return new ArrayList<>();
    }

    public List<Review> getReviewsByUser() {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.getReviewsByUser(userId);
        }
        return new ArrayList<>();
    }

    public int getReviewCount() {
        int userId = getCurrentUserId();
        if (userId > 0 && dbHelper != null) {
            return dbHelper.getReviewCount(userId);
        }
        return 0;
    }

    public int getReviewsCount() {
        return getReviewCount();
    }

    public List<Review> getAllReviews() {
        return getReviewsByUser();
    }

    // Statistics
    public float getAverageRating() {
        List<Review> userReviews = getReviewsByUser();
        if (userReviews.isEmpty()) return 0.0f;
        float total = 0.0f;
        for (Review r : userReviews) {
            total += r.getRating();
        }
        return total / userReviews.size();
    }
}