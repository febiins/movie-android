package com.example.movue.data.api;

import com.example.movue.model.Movie;
import com.example.movue.model.Review;
import com.example.movue.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("auth/login/")
    Call<User> login(@Body User user);

    @POST("auth/register/")
    Call<User> register(@Body User user);

    @GET("movies/")
    Call<List<Movie>> getMovies();

    @GET("movies/{id}/")
    Call<Movie> getMovieDetails(@Path("id") int movieId);

    @GET("movies/search/")
    Call<List<Movie>> searchMovies(@Query("q") String query);

    @POST("reviews/")
    Call<Review> createReview(@Body Review review);

    @GET("reviews/")
    Call<List<Review>> getReviews(@Query("movie_id") Integer movieId);
}