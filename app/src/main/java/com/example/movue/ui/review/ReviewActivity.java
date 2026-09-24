package com.example.movue.ui.review;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movue.R;
import com.example.movue.utils.MovieManager;
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

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        long result = MovieManager.getInstance(this).addReview(movieId, rating, reviewText, date);

        if (result != -1) {
            Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to submit review", Toast.LENGTH_SHORT).show();
        }
    }
}