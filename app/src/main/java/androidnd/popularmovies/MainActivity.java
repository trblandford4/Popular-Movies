package androidnd.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.net.URL;
import java.util.Arrays;

import androidnd.popularmovies.DataTypes.Movie;
import androidnd.popularmovies.Utilities.FetchMovieDataTask;
import androidnd.popularmovies.Utilities.MovieDBJsonUtils;
import androidnd.popularmovies.Utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_discovery);

        mGridView = (GridView) findViewById(R.id.gv_movies_grid);

        new FetchMovieDataTask().execute("popular");

    }

    class FetchMovieDataTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            boolean popularSelected = params[0].equals("popular");
            URL movieRequestUrl = NetworkUtils.buildUrl(popularSelected);

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

        // TODO: Update UI after task completed.
        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovieAdapter = new MovieAdapter(MainActivity.this, movies);
                mGridView.setAdapter(mMovieAdapter);

            }
        }
    }
}
