package com.example.movue.data;

import com.example.movue.R;
import com.example.movue.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieData {

    public static List<Movie> getSampleMovies() {
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie(
                1,
                "Interstellar",
                "When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.",
                2014,
                "Sci-Fi, Adventure",
                "169 min",
                8.7,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                2,
                "Inception",
                "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                2010,
                "Action, Sci-Fi",
                "148 min",
                8.8,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                3,
                "The Dark Knight",
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                2008,
                "Action, Crime, Drama",
                "152 min",
                9.0,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                4,
                "Oppenheimer",
                "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.",
                2023,
                "Biography, Drama, History",
                "180 min",
                8.9,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                5,
                "Parasite",
                "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
                2019,
                "Drama, Thriller",
                "132 min",
                8.5,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                6,
                "Whiplash",
                "A promising young drummer enlists at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor who will stop at nothing to realize a student's potential.",
                2014,
                "Drama, Music",
                "106 min",
                8.5,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                7,
                "The Matrix",
                "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence.",
                1999,
                "Action, Sci-Fi",
                "136 min",
                8.7,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                8,
                "Fight Club",
                "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.",
                1999,
                "Drama",
                "139 min",
                8.8,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                9,
                "The Shawshank Redemption",
                "Over the course of several years, two convicts form a friendship, seeking consolation and eventual redemption through basic compassion.",
                1994,
                "Drama",
                "142 min",
                9.3,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                10,
                "Avengers: Endgame",
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more.",
                2019,
                "Action, Adventure, Sci-Fi",
                "181 min",
                8.4,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                11,
                "Spider-Man: No Way Home",
                "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear.",
                2021,
                "Action, Adventure, Fantasy",
                "148 min",
                8.2,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                12,
                "The Godfather",
                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                1972,
                "Crime, Drama",
                "175 min",
                9.2,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                13,
                "Joker",
                "A mentally troubled stand-up comedian embarks on a downward spiral that leads to the creation of an iconic villain.",
                2019,
                "Crime, Drama, Thriller",
                "122 min",
                8.4,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                14,
                "Dune",
                "A noble family becomes embroiled in a war for control over the galaxy's most valuable asset while its heir becomes troubled by visions of a dark future.",
                2021,
                "Action, Adventure, Sci-Fi",
                "155 min",
                8.0,
                R.drawable.ic_movie_placeholder
        ));

        movies.add(new Movie(
                15,
                "Your Name",
                "Two strangers find themselves linked in a bizarre way. When a connection forms, will distance be the only thing to keep them apart?",
                2016,
                "Animation, Drama, Fantasy",
                "106 min",
                8.4,
                R.drawable.ic_movie_placeholder
        ));

        return movies;
    }
}