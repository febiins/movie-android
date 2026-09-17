package com.example.movue.ui.watchlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movue.R;
import com.example.movue.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnWatchlistActionListener listener;

    public interface OnWatchlistActionListener {
        void onMovieClick(Movie movie);
        void onRemoveClick(Movie movie);
    }

    public void setOnWatchlistActionListener(OnWatchlistActionListener listener) {
        this.listener = listener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watchlist, parent, false);
        return new WatchlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText(movie.getReleaseYear());
        holder.ivPoster.setImageResource(R.drawable.ic_movie_placeholder);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClick(movie);
            }
        });

        holder.ivRemove.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class WatchlistViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster, ivRemove;
        TextView tvTitle, tvYear;

        public WatchlistViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvYear = itemView.findViewById(R.id.tvYear);
        }
    }
}