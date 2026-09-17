package com.example.movue.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movue.data.database.WatchlistDao;
import com.example.movue.model.Movie;
import com.example.movue.model.Watchlist;
import com.example.movue.utils.Resource;
import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository {
    private WatchlistDao watchlistDao;

    public WatchlistRepository(WatchlistDao watchlistDao) {
        this.watchlistDao = watchlistDao;
    }

    public LiveData<Resource<List<Movie>>> getWatchlist(int userId) {
        MutableLiveData<Resource<List<Movie>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        // Mock watchlist
        List<Movie> watchlist = new ArrayList<>();
        result.setValue(Resource.success(watchlist));
        return result;
    }

    public LiveData<Resource<Boolean>> addToWatchlist(Watchlist watchlist) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        // Mock add
        result.setValue(Resource.success(true));
        return result;
    }

    public LiveData<Resource<Boolean>> removeFromWatchlist(Watchlist watchlist) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        // Mock remove
        result.setValue(Resource.success(true));
        return result;
    }
}