package com.example.movue.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movue.R;
import com.example.movue.ui.auth.LoginActivity;
import com.example.movue.ui.watchlist.WatchlistFragment;
import com.example.movue.utils.MovieManager;
import com.example.movue.utils.PreferenceManager;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class ProfileFragment extends Fragment {




    private TextView tvUsername;
    private TextView tvWatchedCount;
    private TextView tvReviewCount;
    private TextView tvWatchlistCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvWatchedCount = view.findViewById(R.id.tvWatchedCount);
        tvReviewCount = view.findViewById(R.id.tvReviewCount);
        tvWatchlistCount = view.findViewById(R.id.tvWatchlistCount);
        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);
        View llStats = view.findViewById(R.id.llStats);

        btnLogout.setOnClickListener(v -> performLogout());

        if (llStats != null) {
            llStats.setOnClickListener(v -> openWatchlist());
        }

        updateProfile();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateProfile();
    }

    private void updateProfile() {
        PreferenceManager pref = PreferenceManager.getInstance(requireContext());
        MovieManager manager = MovieManager.getInstance();

        tvUsername.setText(pref.getUsername());
        tvWatchedCount.setText(String.format(Locale.getDefault(), "%d", manager.getWatchedCount()));
        tvReviewCount.setText(String.format(Locale.getDefault(), "%d", manager.getReviewsCount()));
        tvWatchlistCount.setText(String.format(Locale.getDefault(), "%d", manager.getWatchlistCount()));
    }

    private void openWatchlist() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new WatchlistFragment())
                .addToBackStack(null)
                .commit();
    }

    private void performLogout() {
        PreferenceManager.getInstance(requireContext()).logout();
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}