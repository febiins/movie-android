package com.example.movue.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.movue.data.api.ApiClient;
import com.example.movue.data.api.ApiService;
import com.example.movue.data.api.AuthInterceptor;
import com.example.movue.data.database.AppDatabase;
import com.example.movue.data.local.PreferencesManager;
import com.example.movue.data.repository.AuthRepository;
import com.example.movue.data.repository.DiaryRepository;
import com.example.movue.data.repository.MovieRepository;
import com.example.movue.data.repository.ReviewRepository;
import com.example.movue.data.repository.WatchlistRepository;
import com.example.movue.utils.TokenManager;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        AppDatabase database = AppDatabase.getDatabase(context);
        TokenManager tokenManager = new TokenManager(context);
        AuthInterceptor authInterceptor = new AuthInterceptor(tokenManager);
        ApiService apiService = ApiClient.getClient(authInterceptor).create(ApiService.class);
        PreferencesManager preferencesManager = new PreferencesManager(context);

        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(new AuthRepository(apiService, preferencesManager));
        } else if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(new MovieRepository(apiService, database.movieDao()));
        } else if (modelClass.isAssignableFrom(ReviewViewModel.class)) {
            return (T) new ReviewViewModel(new ReviewRepository(apiService, database.reviewDao()));
        } else if (modelClass.isAssignableFrom(WatchlistViewModel.class)) {
            return (T) new WatchlistViewModel(new WatchlistRepository(database.watchlistDao()));
        } else if (modelClass.isAssignableFrom(DiaryViewModel.class)) {
            return (T) new DiaryViewModel(new DiaryRepository(database.watchedMovieDao()));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}