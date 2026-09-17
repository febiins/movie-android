package com.example.movue.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.movue.data.repository.WatchlistRepository;
import com.example.movue.model.Movie;
import com.example.movue.model.Watchlist;
import com.example.movue.utils.Resource;
import java.util.List;

public class WatchlistViewModel extends ViewModel {
    private WatchlistRepository repository;

    public WatchlistViewModel(WatchlistRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<Movie>>> getWatchlist(int userId) {
        return repository.getWatchlist(userId);
    }

    public LiveData<Resource<Boolean>> addToWatchlist(Watchlist watchlist) {
        return repository.addToWatchlist(watchlist);
    }

    public LiveData<Resource<Boolean>> removeFromWatchlist(Watchlist watchlist) {
        return repository.removeFromWatchlist(watchlist);
    }
}