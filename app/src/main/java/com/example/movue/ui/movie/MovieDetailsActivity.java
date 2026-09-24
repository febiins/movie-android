package com.example.movue.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movue.R;
import com.example.movue.model.Movie;
import com.example.movue.model.Review;
import com.example.movue.ui.review.ReviewActivity;
import com.example.movue.ui.review.ReviewAdapter;
import com.example.movue.utils.MovieManager;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView ivPosterLarge;
    private TextView tvMovieTitle, tvMovieInfo, tvDescription;
    private MaterialButton btnWatchlist, btnWatched, btnReview;
    private ReviewAdapter reviewAdapter;
    private int movieId;
    private Movie currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieId = getIntent().getIntExtra("movie_id", -1);
        currentMovie = MovieManager.getInstance(this).getMovieById(movieId);

        if (currentMovie == null) {
            Toast.makeText(this, "Movie not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ivPosterLarge = findViewById(R.id.ivPosterLarge);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieInfo = findViewById(R.id.tvMovieInfo);
        tvDescription = findViewById(R.id.tvDescription);
        btnWatchlist = findViewById(R.id.btnWatchlist);
        btnWatched = findViewById(R.id.btnWatched);
        btnReview = findViewById(R.id.btnReview);
        RecyclerView rvReviews = findViewById(R.id.rvReviews);

        reviewAdapter = new ReviewAdapter();
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        rvReviews.setAdapter(reviewAdapter);

        populateMovieDetails();
        updateButtonStates();

        btnWatchlist.setOnClickListener(v -> toggleWatchlist());
        btnWatched.setOnClickListener(v -> toggleWatched());
        btnReview.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("movie_id", movieId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReviews();
        updateButtonStates();
    }

    private void populateMovieDetails() {
        tvMovieTitle.setText(currentMovie.getTitle());
        tvMovieInfo.setText(String.format(Locale.getDefault(), "%d | %s | %s | ★ %.1f",
                currentMovie.getReleaseYear(), currentMovie.getGenre(), currentMovie.getRuntime(), currentMovie.getRating()));
        tvDescription.setText(currentMovie.getDescription());
        ivPosterLarge.setImageResource(currentMovie.getPosterResId());
    }

    private void updateButtonStates() {
        MovieManager manager = MovieManager.getInstance(this);

        if (manager.isInWatchlist(movieId)) {
            btnWatchlist.setText("Remove from Watchlist");
        } else {
            btnWatchlist.setText(R.string.watchlist_add);
        }

        if (manager.isWatched(movieId)) {
            btnWatched.setText("Watched ✓");
            btnWatched.setEnabled(false);
        } else {
            btnWatched.setText(R.string.mark_as_watched);
            btnWatched.setEnabled(true);
        }
    }

    private void toggleWatchlist() {
        MovieManager manager = MovieManager.getInstance(this);
        if (manager.isInWatchlist(movieId)) {
            manager.removeFromWatchlist(movieId);
            Toast.makeText(this, "Removed from watchlist", Toast.LENGTH_SHORT).show();
        } else {
            boolean success = manager.addToWatchlist(movieId);
            if (success) {
                Toast.makeText(this, "Added to watchlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add to watchlist", Toast.LENGTH_SHORT).show();
            }
        }
        updateButtonStates();
    }

    private void toggleWatched() {
        MovieManager manager = MovieManager.getInstance(this);
        if (!manager.isWatched(movieId)) {
            String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
            manager.markAsWatched(movieId, date, (float) currentMovie.getRating(), "");
            Toast.makeText(this, "Marked as watched", Toast.LENGTH_SHORT).show();
        }
        updateButtonStates();
    }

    private void loadReviews() {
        List<Review> reviews = MovieManager.getInstance(this).getReviewsForMovie(movieId);
        reviewAdapter.setReviews(reviews);
    }
}