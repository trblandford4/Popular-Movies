package androidnd.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.net.URL;

import androidnd.popularmovies.DataTypes.Movie;
import androidnd.popularmovies.Utilities.MovieDBJsonUtils;
import androidnd.popularmovies.Utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_DETAILS_EXTRA = "MOVIE";


    private GridView mGridView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_discovery);

        mGridView = (GridView) findViewById(R.id.gv_movies_grid);
        mGridView.setOnItemClickListener(movieClickHandler);

        new FetchMovieDataTask().execute("popular");

    }

    private final GridView.OnItemClickListener movieClickHandler = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = (Movie) parent.getItemAtPosition(position);
            Intent launchMovieDetailsIntent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
            launchMovieDetailsIntent.putExtra(MOVIE_DETAILS_EXTRA, movie);
            startActivity(launchMovieDetailsIntent);

        }
    };


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
