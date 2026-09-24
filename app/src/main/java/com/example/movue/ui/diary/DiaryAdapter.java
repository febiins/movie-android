package com.example.movue.ui.diary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movue.R;
import com.example.movue.model.Movie;
import com.example.movue.model.WatchedMovie;
import com.example.movue.utils.MovieManager;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private List<WatchedMovie> watchedMovies = new ArrayList<>();
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public DiaryAdapter(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public void setWatchedMovies(List<WatchedMovie> watchedMovies) {
        this.watchedMovies = watchedMovies != null ? watchedMovies : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        WatchedMovie watched = watchedMovies.get(position);
        Movie movie = MovieManager.getInstance().getMovieById(watched.getMovieId());

        if (movie != null) {
            holder.tvTitle.setText(movie.getTitle());
            holder.ivPoster.setImageResource(movie.getPosterResId());
        } else {
            holder.tvTitle.setText("Unknown Movie");
            holder.ivPoster.setImageResource(R.drawable.ic_movie_placeholder);
        }

        holder.tvDate.setText(watched.getWatchedDate());
        holder.ratingBar.setRating(watched.getRating());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null && movie != null) {
                listener.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return watchedMovies.size();
    }

    static class DiaryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvDate;
        RatingBar ratingBar;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}