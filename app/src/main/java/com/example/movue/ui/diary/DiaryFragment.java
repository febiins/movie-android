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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movue.R;
import com.example.movue.data.local.PreferencesManager;
import com.example.movue.model.Movie;
import com.example.movue.ui.movie.MovieDetailsActivity;
import com.example.movue.viewmodel.DiaryViewModel;
import com.example.movue.viewmodel.ViewModelFactory;

public class DiaryFragment extends Fragment implements DiaryAdapter.OnMovieClickListener {
    private DiaryViewModel diaryViewModel;
    private DiaryAdapter diaryAdapter;
    private LinearLayout llEmptyDiary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        RecyclerView rvDiary = view.findViewById(R.id.rvDiary);
        llEmptyDiary = view.findViewById(R.id.llEmptyDiary);

        diaryAdapter = new DiaryAdapter();
        diaryAdapter.setOnMovieClickListener(this);
        rvDiary.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvDiary.setAdapter(diaryAdapter);

        diaryViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(DiaryViewModel.class);
        
        loadDiary();

        return view;
    }

    private void loadDiary() {
        int userId = 1; // Mock user ID
        diaryViewModel.getWatchedMovies(userId).observe(getViewLifecycleOwner(), resource -> {
            if (resource.data != null && !resource.data.isEmpty()) {
                diaryAdapter.setMovies(resource.data);
                llEmptyDiary.setVisibility(View.GONE);
            } else {
                llEmptyDiary.setVisibility(View.VISIBLE);
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