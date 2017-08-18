package androidnd.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidnd.popularmovies.datatypes.Movie;

/**
 * Created by rayblandford on 5/29/17.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Movie[] mMovies;

    public MovieAdapter(Context context, Movie[] movieList) {
        super(context, 0, movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.iv_movie_poster);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String posterUrl = movie.getFullPosterURL();
        System.out.print(posterUrl);
        Picasso.with(getContext()).load(posterUrl).placeholder(R.drawable.movie_poster_loading).into(viewHolder.poster);

        return convertView;
    }

    public void setMovieData(Movie[] movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView poster;
    }
}
