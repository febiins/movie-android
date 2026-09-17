package com.example.movue.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movue.R;
import com.example.movue.model.Movie;
import com.example.movue.ui.review.ReviewActivity;
import com.example.movue.ui.review.ReviewAdapter;
import com.example.movue.viewmodel.MovieViewModel;
import com.example.movue.viewmodel.ReviewViewModel;
import com.example.movue.viewmodel.ViewModelFactory;
import com.google.android.material.button.MaterialButton;

public class MovieDetailsActivity extends AppCompatActivity {
    private MovieViewModel movieViewModel;
    private ReviewViewModel reviewViewModel;
    private ImageView ivPosterLarge;
    private TextView tvMovieTitle, tvMovieInfo, tvDescription;
    private MaterialButton btnWatchlist, btnWatched, btnReview;
    private ReviewAdapter reviewAdapter;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieId = getIntent().getIntExtra("movie_id", -1);

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

        ViewModelFactory factory = new ViewModelFactory(this);
        movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
        reviewViewModel = new ViewModelProvider(this, factory).get(ReviewViewModel.class);

        loadMovieDetails();
        loadReviews();

        btnReview.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("movie_id", movieId);
            startActivity(intent);
        });
    }

    private void loadMovieDetails() {
        movieViewModel.getMovieDetails(movieId).observe(this, resource -> {
            if (resource.data != null) {
                Movie movie = resource.data;
                tvMovieTitle.setText(movie.getTitle());
                tvMovieInfo.setText(String.format("%s | %s | %s", movie.getReleaseYear(), movie.getGenre(), movie.getRuntime()));
                tvDescription.setText(movie.getDescription());
                ivPosterLarge.setImageResource(R.drawable.ic_movie_placeholder);
            }
        });
    }

    private void loadReviews() {
        reviewViewModel.getReviewsForMovie(movieId).observe(this, resource -> {
            if (resource.data != null) {
                reviewAdapter.setReviews(resource.data);
            }
        });
    }
}