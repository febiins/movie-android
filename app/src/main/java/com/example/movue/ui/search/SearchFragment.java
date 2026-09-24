package com.example.movue.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SearchFragment extends Fragment implements SearchAdapter.OnMovieClickListener {

    private SearchAdapter searchAdapter;
    private TextView tvEmptySearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        TextInputEditText etSearch = view.findViewById(R.id.etSearch);
        RecyclerView rvSearchResults = view.findViewById(R.id.rvSearchResults);
        tvEmptySearch = view.findViewById(R.id.tvEmptySearch);

        searchAdapter = new SearchAdapter(this);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSearchResults.setAdapter(searchAdapter);

        // Display all movies initially
        performSearch("");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s != null ? s.toString() : "");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void performSearch(String query) {
        MovieManager manager = MovieManager.getInstance(requireContext());
        List<Movie> results;
        if (query.trim().isEmpty()) {
            results = manager.getAllMovies();
        } else {
            results = manager.searchMovies(query);
        }

        searchAdapter.setMovies(results);

        if (results.isEmpty()) {
            tvEmptySearch.setVisibility(View.VISIBLE);
            tvEmptySearch.setText(R.string.search_hint);
        } else {
            tvEmptySearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getId());
        startActivity(intent);
    }
}