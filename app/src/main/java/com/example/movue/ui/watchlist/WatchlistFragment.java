package com.example.movue.ui.watchlist;

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
import com.example.movue.model.Movie;
import com.example.movue.ui.movie.MovieDetailsActivity;
import com.example.movue.viewmodel.WatchlistViewModel;
import com.example.movue.viewmodel.ViewModelFactory;

public class WatchlistFragment extends Fragment implements WatchlistAdapter.OnWatchlistActionListener {
    private WatchlistViewModel watchlistViewModel;
    private WatchlistAdapter watchlistAdapter;
    private TextView tvEmptyWatchlist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        RecyclerView rvWatchlist = view.findViewById(R.id.rvWatchlist);
        tvEmptyWatchlist = view.findViewById(R.id.tvEmptyWatchlist);

        watchlistAdapter = new WatchlistAdapter();
        watchlistAdapter.setOnWatchlistActionListener(this);
        rvWatchlist.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvWatchlist.setAdapter(watchlistAdapter);

        watchlistViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(WatchlistViewModel.class);
        
        loadWatchlist();

        return view;
    }

    private void loadWatchlist() {
        int userId = 1; // Mock user ID
        watchlistViewModel.getWatchlist(userId).observe(getViewLifecycleOwner(), resource -> {
            if (resource.data != null && !resource.data.isEmpty()) {
                watchlistAdapter.setMovies(resource.data);
                tvEmptyWatchlist.setVisibility(View.GONE);
            } else {
                tvEmptyWatchlist.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getId());
        startActivity(intent);
    }

    @Override
    public void onRemoveClick(Movie movie) {
        // Handle remove from watchlist
    }
}