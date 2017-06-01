package androidnd.popularmovies.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidnd.popularmovies.DataTypes.Movie;

/**
 * Utility functions to handle The Movie Database JSON data.
 */
public final class MovieDBJsonUtils {
    /**
     * This method parses JSON from a web response and returns an array of Movie objects
     * based on the user's discover preference.
     *
     * @param moviesJsonStr JSON response from server
     * @return Array of Movies
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getMovieDataFromJson(String moviesJsonStr)
            throws JSONException {

        final String MDB_RESULTS = "results";
        final String MDP_ID = "id";
        final String MDP_POSTER_PATH = "poster_path";
        final String MDP_OVERVIEW = "overview";
        final String MDP_OG_TITLE = "original_title";
        final String MDP_RELEASE_DATE = "release_date";
        final String MDP_POPULARITY = "popularity";
        final String MDP_VOTE_COUNT = "vote_count";
        final String MDP_VOTE_AVG = "vote_average";

        Movie[] parsedMovieData = null;

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray movieArray = moviesJson.getJSONArray(MDB_RESULTS);

        parsedMovieData = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            /* These are the values that will be collected */
            int id;
            String posterPath;
            String ogTitle;
            String overview;
            String releaseDate;
            int voteCount;
            double popularity;
            double voteAverage;

            /* Get the JSON object representing the day */
            JSONObject currMovie = movieArray.getJSONObject(i);

            id = currMovie.getInt(MDP_ID);
            posterPath = currMovie.getString(MDP_POSTER_PATH);
            ogTitle = currMovie.getString(MDP_OG_TITLE);
            overview = currMovie.getString(MDP_OVERVIEW);
            releaseDate = currMovie.getString(MDP_RELEASE_DATE);
            voteCount = currMovie.getInt(MDP_VOTE_COUNT);
            popularity = currMovie.getDouble(MDP_POPULARITY);
            voteAverage = currMovie.getDouble(MDP_VOTE_AVG);


            parsedMovieData[i] = new Movie(id, posterPath, ogTitle, overview, releaseDate,
                    popularity, voteCount, voteAverage);
        }

        return parsedMovieData;
    }

}
