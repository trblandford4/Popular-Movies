package androidnd.popularmovies.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

import java.net.URL;

import androidnd.popularmovies.MainActivity;
import androidnd.popularmovies.MovieAdapter;
import androidnd.popularmovies.datatypes.Movie;

/**
 * Created by rayblandford on 8/17/17.
 */

public class FetchMovieDataTask extends AsyncTask<String, Void, Movie[]> {
    private MovieAdapter mMovieAdapter;
    private GridView mGridView;
    private Context mContext;

    public FetchMovieDataTask(MovieAdapter movieAdapter, GridView gridView, Context context) {
        mMovieAdapter = movieAdapter;
        mGridView = gridView;
        mContext = context;
    }

    @Override
    protected Movie[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
        if (params.length == 0) {
            return null;
        }

        boolean popularSelected = params[0].equals("popular");
        URL movieRequestUrl = NetworkUtils.buildUrl(popularSelected, mContext);

        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieRequestUrl);

            Movie[] jsonMovieData = MovieDBJsonUtils
                    .getMovieDataFromJson(jsonMovieResponse);

            return jsonMovieData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        if (movies != null) {
            mMovieAdapter = new MovieAdapter(mContext, movies);
            mGridView.setAdapter(mMovieAdapter);
        }
    }
}