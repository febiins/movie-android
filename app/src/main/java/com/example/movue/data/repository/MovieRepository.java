package com.example.movue.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.movue.data.api.ApiService;
import com.example.movue.data.database.MovieDao;
import com.example.movue.model.Movie;
import com.example.movue.utils.Resource;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private ApiService apiService;
    private MovieDao movieDao;

    public MovieRepository(ApiService apiService, MovieDao movieDao) {
        this.apiService = apiService;
        this.movieDao = movieDao;
    }

    public LiveData<Resource<List<Movie>>> getMovies() {
        MutableLiveData<Resource<List<Movie>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));

        // Mock data
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", "https://example.com/interstellar.jpg", "2014", "Sci-Fi", "169 min"));
        movies.add(new Movie(2, "Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", "https://example.com/inception.jpg", "2010", "Sci-Fi", "148 min"));
        movies.add(new Movie(3, "The Dark Knight", "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", "https://example.com/tdk.jpg", "2008", "Action", "152 min"));
        movies.add(new Movie(4, "Oppenheimer", "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.", "https://example.com/oppenheimer.jpg", "2023", "Drama", "180 min"));
        movies.add(new Movie(5, "Parasite", "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.", "https://example.com/parasite.jpg", "2019", "Thriller", "132 min"));
        
        result.setValue(Resource.success(movies));
        return result;
    }

    public LiveData<Resource<Movie>> getMovieDetails(int movieId) {
        MutableLiveData<Resource<Movie>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        // In real app, fetch from Room or API
        // For mock, just return a movie
        Movie movie = new Movie(movieId, "Mock Movie", "Description", "url", "2024", "Genre", "120 min");
        result.setValue(Resource.success(movie));
        return result;
    }

    public LiveData<Resource<List<Movie>>> searchMovies(String query) {
        MutableLiveData<Resource<List<Movie>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading(null));
        // Mock search
        List<Movie> movies = new ArrayList<>();
        result.setValue(Resource.success(movies));
        return result;
    }
}