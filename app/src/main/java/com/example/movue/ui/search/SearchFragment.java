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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movue.R;
import com.example.movue.model.Movie;
import com.example.movue.ui.movie.MovieDetailsActivity;
import com.example.movue.viewmodel.MovieViewModel;
import com.example.movue.viewmodel.ViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment implements SearchAdapter.OnMovieClickListener {
    private MovieViewModel movieViewModel;
    private SearchAdapter searchAdapter;
    private TextView tvEmptySearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        TextInputEditText etSearch = view.findViewById(R.id.etSearch);
        RecyclerView rvSearchResults = view.findViewById(R.id.rvSearchResults);
        tvEmptySearch = view.findViewById(R.id.tvEmptySearch);

        searchAdapter = new SearchAdapter();
        searchAdapter.setOnMovieClickListener(this);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSearchResults.setAdapter(searchAdapter);

        movieViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(MovieViewModel.class);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    performSearch(s.toString());
                } else {
                    searchAdapter.setMovies(null);
                    tvEmptySearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void performSearch(String query) {
        movieViewModel.searchMovies(query).observe(getViewLifecycleOwner(), resource -> {
            if (resource.data != null && !resource.data.isEmpty()) {
                searchAdapter.setMovies(resource.data);
                tvEmptySearch.setVisibility(View.GONE);
            } else {
                tvEmptySearch.setVisibility(View.VISIBLE);
                tvEmptySearch.setText("No movies found");
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