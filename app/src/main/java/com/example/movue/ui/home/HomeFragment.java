package com.example.movue.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movue.R;
import com.example.movue.data.local.PreferencesManager;
import com.example.movue.model.Movie;
import com.example.movue.ui.movie.MovieDetailsActivity;
import com.example.movue.viewmodel.MovieViewModel;
import com.example.movue.viewmodel.ViewModelFactory;
import java.util.List;

public class HomeFragment extends Fragment implements MovieAdapter.OnMovieClickListener {
    private MovieViewModel movieViewModel;
    private MovieAdapter featuredAdapter, curatedAdapter, recentlyWatchedAdapter;
    private TextView tvGreeting;
    private PreferencesManager preferencesManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        tvGreeting = view.findViewById(R.id.tvGreeting);
        RecyclerView rvFeatured = view.findViewById(R.id.rvFeatured);
        RecyclerView rvCurated = view.findViewById(R.id.rvCurated);
        RecyclerView rvRecentlyWatched = view.findViewById(R.id.rvRecentlyWatched);

        preferencesManager = new PreferencesManager(requireContext());
        tvGreeting.setText(getString(R.string.greeting, preferencesManager.getUsername()));

        featuredAdapter = new MovieAdapter();
        featuredAdapter.setOnMovieClickListener(this);
        rvFeatured.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFeatured.setAdapter(featuredAdapter);

        curatedAdapter = new MovieAdapter();
        curatedAdapter.setOnMovieClickListener(this);
        rvCurated.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCurated.setAdapter(curatedAdapter);

        recentlyWatchedAdapter = new MovieAdapter();
        recentlyWatchedAdapter.setOnMovieClickListener(this);
        rvRecentlyWatched.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rvRecentlyWatched.setAdapter(recentlyWatchedAdapter);

        movieViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(MovieViewModel.class);
        
        observeMovies();

        return view;
    }

    private void observeMovies() {
        movieViewModel.getMovies().observe(getViewLifecycleOwner(), resource -> {
            if (resource.data != null) {
                featuredAdapter.setMovies(resource.data);
                curatedAdapter.setMovies(resource.data);
                recentlyWatchedAdapter.setMovies(resource.data);
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getId());
        startActivity(intent);
    }
}