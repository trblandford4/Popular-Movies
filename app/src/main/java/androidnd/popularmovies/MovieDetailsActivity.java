package androidnd.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidnd.popularmovies.datatypes.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {
    @BindView(R.id.tv_movie_title) TextView tv_movieTitle;
    @BindView(R.id.tv_avg_rating) TextView tv_movieRating;
    @BindView(R.id.tv_overview) TextView tv_movieOverview;
    @BindView(R.id.iv_movie_poster_details) ImageView iv_moviePoster;
    @BindView(R.id.tv_release_date) TextView tv_movieReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent launchingIntent = getIntent();
        Movie movie = launchingIntent.getParcelableExtra(MainActivity.MOVIE_DETAILS_EXTRA);

        ButterKnife.bind(this);
        tv_movieTitle.setText(movie.getOgTitle());
        tv_movieRating.setText(movie.getVoteAverage());
        tv_movieOverview.setText(movie.getOverview());
        tv_movieReleaseDate.setText(movie.getReleaseDate());
        Picasso.with(this).load(movie.getFullPosterURL()).into(iv_moviePoster);
    }
}
