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
import com.example.movue.data.local.PreferencesManager;
import com.example.movue.ui.auth.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {
    private PreferencesManager preferencesManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        preferencesManager = new PreferencesManager(requireContext());

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);

        tvUsername.setText(preferencesManager.getUsername());

        btnLogout.setOnClickListener(v -> {
            preferencesManager.clear();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}