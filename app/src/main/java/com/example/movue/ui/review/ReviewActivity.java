package com.example.movue.ui.review;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.movue.R;
import com.example.movue.model.Review;
import com.example.movue.viewmodel.ReviewViewModel;
import com.example.movue.viewmodel.ViewModelFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReviewActivity extends AppCompatActivity {
    private ReviewViewModel reviewViewModel;
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

        reviewViewModel = new ViewModelProvider(this, new ViewModelFactory(this)).get(ReviewViewModel.class);

        btnSubmitReview.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String reviewText = etReviewText.getText().toString();

            if (rating == 0) {
                Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show();
                return;
            }

            String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
            Review review = new Review(1, movieId, rating, reviewText, date, date); // Mock userId = 1

            reviewViewModel.createReview(review).observe(this, resource -> {
                switch (resource.status) {
                    case LOADING:
                        btnSubmitReview.setEnabled(false);
                        break;
                    case SUCCESS:
                        Toast.makeText(this, "Review submitted", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case ERROR:
                        btnSubmitReview.setEnabled(true);
                        Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        });
    }
}