package androidnd.popularmovies.DataTypes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Custom Java Object to represent a Movie.
 */
public class Movie implements Parcelable {
    private int id;
    private String posterPath;
    private String ogTitle;
    private String overview;
    private String releaseDate;
    private double popularity;
    private int voteCount;
    private double voteAverage;

    /**
     * Custom Constructor that populates all fields for a Movie object.
     *
     * @param id          The unique identifier for the movie in the Movie Database
     * @param posterPath  the path to append to the URL for the Movie Poster Image
     * @param ogTitle     the original title of the movie
     * @param overview    the movie's short plot description
     * @param releaseDate the release date of the movie
     * @param popularity  the popularity of the movie
     * @param voteCount   the number of votes on the movie
     * @param voteAverage the average rating of the movie
     */
    public Movie(int id, String posterPath, String ogTitle, String overview, String releaseDate,
                 double popularity, int voteCount, double voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.ogTitle = ogTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }

    private Movie (Parcel parcel) {
        posterPath = parcel.readString();
        ogTitle = parcel.readString();
        overview = parcel.readString();
        voteAverage = parcel.readDouble();
        releaseDate = parcel.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(posterPath);
        parcel.writeString(ogTitle);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeDouble(voteAverage);
    }

    public String getFullPosterURL() {
        return "http://image.tmdb.org/t/p/w185/" + posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOgTitle() {
        return ogTitle;
    }

    public void setOgTitle(String ogTitle) {
        this.ogTitle = ogTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
