package com.example.movue.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movue.data.api.ApiService;
import com.example.movue.data.database.ReviewDao;
import com.example.movue.model.Review;
import com.example.movue.utils.Resource;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {
    private ApiService apiService;
    private ReviewDao reviewDao;

    public ReviewRepository(ApiService apiService, ReviewDao reviewDao) {
        this.apiService = apiService;
        this.reviewDao = reviewDao;
    }

    public LiveData<Resource<Review>> createReview(Review review) {
        MutableLiveData<Resource<Review>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        // Mock save
        result.setValue(Resource.success(review));
        return result;
    }

    public LiveData<Resource<List<Review>>> getReviewsForMovie(int movieId) {
        MutableLiveData<Resource<List<Review>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        // Mock reviews
        List<Review> reviews = new ArrayList<>();
        result.setValue(Resource.success(reviews));
        return result;
    }
}