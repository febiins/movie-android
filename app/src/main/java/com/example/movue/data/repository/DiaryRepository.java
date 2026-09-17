package com.example.movue.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movue.data.database.WatchedMovieDao;
import com.example.movue.model.Movie;
import com.example.movue.model.WatchedMovie;
import com.example.movue.utils.Resource;
import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {
    private WatchedMovieDao watchedMovieDao;

    public DiaryRepository(WatchedMovieDao watchedMovieDao) {
        this.watchedMovieDao = watchedMovieDao;
    }

    public LiveData<Resource<List<Movie>>> getWatchedMovies(int userId) {
        MutableLiveData<Resource<List<Movie>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        // Mock diary
        List<Movie> watched = new ArrayList<>();
        result.setValue(Resource.success(watched));
        return result;
    }

    public LiveData<Resource<Boolean>> markAsWatched(WatchedMovie watchedMovie) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        // Mock mark
        result.setValue(Resource.success(true));
        return result;
    }
}