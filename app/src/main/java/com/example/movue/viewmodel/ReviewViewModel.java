package com.example.movue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movue.data.repository.ReviewRepository;
import com.example.movue.model.Review;
import com.example.movue.utils.Resource;
import java.util.List;

public class ReviewViewModel extends ViewModel {
    private ReviewRepository repository;

    public ReviewViewModel(ReviewRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<Review>> createReview(Review review) {
        return repository.createReview(review);
    }

    public LiveData<Resource<List<Review>>> getReviewsForMovie(int movieId) {
        return repository.getReviewsForMovie(movieId);
    }
}