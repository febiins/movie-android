package com.example.movue.ui.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movue.R;
import com.example.movue.model.Movie;
import com.example.movue.model.WatchedMovie;
import com.example.movue.ui.movie.MovieDetailsActivity;
import com.example.movue.utils.MovieManager;

import java.util.List;

public class DiaryFragment extends Fragment implements DiaryAdapter.OnMovieClickListener {

    private DiaryAdapter diaryAdapter;
    private LinearLayout llEmptyDiary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        RecyclerView rvDiary = view.findViewById(R.id.rvDiary);
        llEmptyDiary = view.findViewById(R.id.llEmptyDiary);

        diaryAdapter = new DiaryAdapter(this);
        rvDiary.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvDiary.setAdapter(diaryAdapter);

        loadDiary();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDiary();
    }

    private void loadDiary() {
        List<WatchedMovie> watchedMovies = MovieManager.getInstance(requireContext()).getWatchedMovies();
        if (watchedMovies == null || watchedMovies.isEmpty()) {
            llEmptyDiary.setVisibility(View.VISIBLE);
            diaryAdapter.setWatchedMovies(null);
        } else {
            llEmptyDiary.setVisibility(View.GONE);
            diaryAdapter.setWatchedMovies(watchedMovies);
        }
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movie.getId());
        startActivity(intent);
    }
}