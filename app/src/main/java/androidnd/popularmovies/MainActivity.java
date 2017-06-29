package androidnd.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.net.URL;

import androidnd.popularmovies.DataTypes.Movie;
import androidnd.popularmovies.Utilities.MovieDBJsonUtils;
import androidnd.popularmovies.Utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_DETAILS_EXTRA = "MOVIE";


    private GridView mGridView;
    private MovieAdapter mMovieAdapter;
    private final static String MENU_SELECTED = "selected";
    private int selected = -1;
    MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_discovery);

        mGridView = (GridView) findViewById(R.id.gv_movies_grid);
        mGridView.setOnItemClickListener(movieClickHandler);

        if (savedInstanceState == null) {
            sortByPopular();
        } else {
            selected = savedInstanceState.getInt(MENU_SELECTED);
            if (selected == R.id.sort_popular || selected == -1) {
                sortByPopular();
            } else {
                sortByHighestRated();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_movies_sort, menu);

        if (selected == -1){
            return true;
        }

        switch (selected){
            case R.id.sort_popular:
                mMenuItem = (MenuItem) menu.findItem(R.id.sort_popular);
                mMenuItem.setChecked(true);
                break;

            case R.id.sort_rating:
                mMenuItem = (MenuItem) menu.findItem(R.id.sort_rating);
                mMenuItem.setChecked(true);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mMovieAdapter != null || !isNetworkAvailable()) {
            int id = item.getItemId();
            switch (id) {
                case R.id.sort_popular:
                    mMovieAdapter.setMovieData(null);
                    sortByPopular();
                    selected = id;
                    item.setChecked(true);
                    return true;
                case R.id.sort_rating:
                    mMovieAdapter.setMovieData(null);
                    sortByHighestRated();
                    selected = id;
                    item.setChecked(true);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(MENU_SELECTED, selected);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void sortByHighestRated() {
        if (isNetworkAvailable()) {
            new FetchMovieDataTask().execute("highest-rated");
        } else {
            Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void sortByPopular() {
        if (isNetworkAvailable()) {
            new FetchMovieDataTask().execute("popular");
        } else {
            Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Checks if there is Internet accessible.
     * Based on a stackoverflow snippet
     *
     * @return True if there is Internet. False if not.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
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

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovieAdapter = new MovieAdapter(MainActivity.this, movies);
                mGridView.setAdapter(mMovieAdapter);
            }
        }
    }
}
