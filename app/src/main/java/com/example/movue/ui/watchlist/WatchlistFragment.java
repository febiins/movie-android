package com.example.movue.ui.watchlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movue.R;
import com.example.movue.model.Movie;
import com.example.movue.ui.movie.MovieDetailsActivity;
import com.example.movue.utils.MovieManager;

import java.util.List;

public class WatchlistFragment extends Fragment implements WatchlistAdapter.OnWatchlistActionListener {

    private WatchlistAdapter watchlistAdapter;
    private TextView tvEmptyWatchlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        RecyclerView rvWatchlist = view.findViewById(R.id.rvWatchlist);
        tvEmptyWatchlist = view.findViewById(R.id.tvEmptyWatchlist);

        watchlistAdapter = new WatchlistAdapter(this);
        rvWatchlist.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvWatchlist.setAdapter(watchlistAdapter);

        loadWatchlist();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWatchlist();
    }

    private void loadWatchlist() {
        List<Movie> movies = MovieManager.getInstance(requireContext()).getWatchlistMovies();
        if (movies == null || movies.isEmpty()) {
            tvEmptyWatchlist.setVisibility(View.VISIBLE);
            watchlistAdapter.setMovies(null);
        } else {
            tvEmptyWatchlist.setVisibility(View.GONE);
            watchlistAdapter.setMovies(movies);
        }
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getId());
        startActivity(intent);
    }

    @Override
    public void onRemoveClick(Movie movie) {
        MovieManager.getInstance(requireContext()).removeFromWatchlist(movie.getId());
        Toast.makeText(requireContext(), "Removed from Watchlist", Toast.LENGTH_SHORT).show();
        loadWatchlist();
    }
}