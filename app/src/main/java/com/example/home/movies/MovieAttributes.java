package com.example.home.movies;

/**
 * Created by Windows 7 on 1/4/2016.
 */
public class MovieAttributes {

    private String posterPath;
    private String title;
    private String overview;
    private String averageRate;
    private String releaseDate;
    private String id;

    public MovieAttributes(String movieId, String moviePoster, String movieName, String movieOverview,
                           String movieRate, String movieDate) {

        this.posterPath = moviePoster;
        this.title = movieName;
        this.overview = movieOverview;
        this.averageRate = movieRate;
        this.releaseDate = movieDate;
        this.id = movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getAverageRate() {
        return averageRate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getId(){
        return id;
    }

    //----------------------------------------------------------------------------------------------

    private String movieType;

    private String movie_TrailerName_ReviewAuthorName;
    private String movie_TrailerLink_ReviewContact;

    public MovieAttributes() {
        super();
    }

    public MovieAttributes(String movieType,
                           String movie_TrailerName_ReviewAuthorName,
                           String movie_TrailerLink_ReviewContact) {
        this.movie_TrailerName_ReviewAuthorName = movie_TrailerName_ReviewAuthorName;
        this.movie_TrailerLink_ReviewContact = movie_TrailerLink_ReviewContact;
        this.movieType = movieType;
    }

    public String getType(){
        return movieType;
    }

    public String getMovie_TrailerName_ReviewAuthorName(){ return movie_TrailerName_ReviewAuthorName; }

    public String getMovie_TrailerLink_ReviewContact(){
        return movie_TrailerLink_ReviewContact;
    }
}
