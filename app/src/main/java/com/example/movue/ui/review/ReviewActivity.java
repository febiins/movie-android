package com.example.movue.ui.review;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movue.R;
import com.example.movue.model.Review;
import com.example.movue.utils.MovieManager;
import com.example.movue.utils.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReviewActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private TextInputEditText etReviewText;
    private MaterialButton btnSubmitReview;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        movieId = getIntent().getIntExtra("movie_id", -1);

        ratingBar = findViewById(R.id.ratingBar);
        etReviewText = findViewById(R.id.etReviewText);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);

        btnSubmitReview.setOnClickListener(v -> submitReview());
    }

    private void submitReview() {
        float rating = ratingBar.getRating();
        String reviewText = etReviewText.getText() != null ? etReviewText.getText().toString().trim() : "";

        if (rating == 0.0f) {
            Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(reviewText)) {
            etReviewText.setError("Review text is required");
            etReviewText.requestFocus();
            return;
        }

        String username = PreferenceManager.getInstance(this).getUsername();
        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        Review review = new Review(
                MovieManager.getInstance().getAllReviews().size() + 1,
                movieId,
                username,
                rating,
                reviewText,
                date
        );

        MovieManager.getInstance().addReview(review);
        MovieManager.getInstance().markAsWatched(movieId, date, rating, reviewText);

        Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}