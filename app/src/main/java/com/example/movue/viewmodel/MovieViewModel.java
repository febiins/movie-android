package com.example.movue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movue.data.repository.MovieRepository;
import com.example.movue.model.Movie;
import com.example.movue.utils.Resource;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private MovieRepository repository;

    public MovieViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<Movie>>> getMovies() {
        return repository.getMovies();
    }

    public LiveData<Resource<Movie>> getMovieDetails(int movieId) {
        return repository.getMovieDetails(movieId);
    }

    public LiveData<Resource<List<Movie>>> searchMovies(String query) {
        return repository.searchMovies(query);
    }
}