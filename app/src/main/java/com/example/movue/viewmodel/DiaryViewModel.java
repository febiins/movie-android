package com.example.movue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movue.data.repository.DiaryRepository;
import com.example.movue.model.Movie;
import com.example.movue.model.WatchedMovie;
import com.example.movue.utils.Resource;
import java.util.List;

public class DiaryViewModel extends ViewModel {
    private DiaryRepository repository;

    public DiaryViewModel(DiaryRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<Movie>>> getWatchedMovies(int userId) {
        return repository.getWatchedMovies(userId);
    }

    public LiveData<Resource<Boolean>> markAsWatched(WatchedMovie watchedMovie) {
        return repository.markAsWatched(watchedMovie);
    }
}