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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movue.R;
import com.example.movue.model.Movie;
import com.example.movue.ui.movie.MovieDetailsActivity;
import com.example.movue.utils.MovieManager;
import com.example.movue.utils.PreferenceManager;

import java.util.List;

public class HomeFragment extends Fragment implements MovieAdapter.OnMovieClickListener {

    private MovieAdapter featuredAdapter;
    private MovieAdapter curatedAdapter;
    private MovieAdapter recentlyWatchedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvGreeting = view.findViewById(R.id.tvGreeting);
        RecyclerView rvFeatured = view.findViewById(R.id.rvFeatured);
        RecyclerView rvCurated = view.findViewById(R.id.rvCurated);
        RecyclerView rvRecentlyWatched = view.findViewById(R.id.rvRecentlyWatched);

        String username = PreferenceManager.getInstance(requireContext()).getUsername();
        tvGreeting.setText(getString(R.string.greeting, username));

        featuredAdapter = new MovieAdapter(this);
        curatedAdapter = new MovieAdapter(this);
        recentlyWatchedAdapter = new MovieAdapter(this);

        setupRecyclerView(rvFeatured, featuredAdapter);
        setupRecyclerView(rvCurated, curatedAdapter);
        setupRecyclerView(rvRecentlyWatched, recentlyWatchedAdapter);

        loadData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void setupRecyclerView(RecyclerView recyclerView, MovieAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        MovieManager manager = MovieManager.getInstance(requireContext());
        List<Movie> allMovies = manager.getAllMovies();

        if (allMovies.size() >= 5) {
            featuredAdapter.setMovies(allMovies.subList(0, 5));
            curatedAdapter.setMovies(allMovies.subList(5, Math.min(12, allMovies.size())));
        } else {
            featuredAdapter.setMovies(allMovies);
            curatedAdapter.setMovies(allMovies);
        }

        List<Movie> watched = manager.getWatchedMoviesList();
        recentlyWatchedAdapter.setMovies(watched.isEmpty() ? allMovies.subList(0, Math.min(3, allMovies.size())) : watched);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getId());
        startActivity(intent);
    }
}