package androidnd.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidnd.popularmovies.DataTypes.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView iv_moviePoster;
    private TextView tv_movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tv_movieTitle = (TextView) findViewById(R.id.tv_movie_title);
        iv_moviePoster = (ImageView) findViewById(R.id.iv_movie_poster_details);

        Intent launchingIntent = getIntent();
        Movie movie = launchingIntent.getParcelableExtra(MainActivity.MOVIE_DETAILS_EXTRA);

        tv_movieTitle.setText(movie.getOgTitle());
        Picasso.with(this).load(movie.getFullPosterURL()).into(iv_moviePoster);

    }
}
