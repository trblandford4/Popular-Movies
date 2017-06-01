package androidnd.popularmovies.Utilities;

import android.os.AsyncTask;

import java.net.URL;

import androidnd.popularmovies.DataTypes.Movie;

/**
 * Created by rayblandford on 5/30/17.
 */

public class FetchMovieDataTask extends AsyncTask<String, Void, Movie[]> {

    // TODO: Implement loading indicator
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        mLoadingIndicator.setVisibility(View.VISIBLE);
//    }

    @Override
    protected Movie[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
        if (params.length == 0) {
            return null;
        }

        boolean popularSelected = params[0] == "popular";
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
//    @Override
//    protected void onPostExecute(String[] weatherData) {
////        mLoadingIndicator.setVisibility(View.INVISIBLE);
//        if (weatherData != null) {
//            showWeatherDataView();
//            mForecastAdapter.setWeatherData(weatherData);
//        } else {
//            showErrorMessage();
//        }
//    }
}
