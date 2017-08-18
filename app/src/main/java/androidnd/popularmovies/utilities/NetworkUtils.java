package androidnd.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import androidnd.popularmovies.R;

/**
 * Created by rayblandford on 5/29/17.
 */

public class NetworkUtils {
    private final static String MOVIE_BASE_URL =
            "http://api.themoviedb.org/3/movie/";
    private final static String POP_PATH = "popular";
    private final static String TOP_RATED_PATH = "top_rated";
    private final static String API_KEY_PARAM = "api_key";

    /**
     * Builds the URL used to talk to the Movie Database server.
     *
     * @return The Url to use to query the Movie Database server.
     */
    public static URL buildUrl(boolean popularSelected, Context context) {
        String apiKey = context.getString(R.string.tmdb_api_key);
        Uri builtUri = null;
        if (popularSelected) {
            builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon().appendPath(POP_PATH)
                    .appendQueryParameter(API_KEY_PARAM, apiKey).build();
        } else {
            builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon().appendPath(TOP_RATED_PATH)
                    .appendQueryParameter(API_KEY_PARAM, apiKey).build();
        }
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(10000);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
