package com.example.home.movies.detailsScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.home.movies.MovieAttributes;
import com.example.home.movies.R;

import java.util.ArrayList;

/**
 * Created by Windows 7 on 1/1/2016.
 */
public class MovieDetailsFragmentAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<MovieAttributes> movieTrailersANDReviews;

    public MovieDetailsFragmentAdapter(Context context, ArrayList<MovieAttributes> movieTrailersANDReviews)
    {
        this.mContext = context;
        this.movieTrailersANDReviews = movieTrailersANDReviews;
    }

    @Override
    public int getCount() {
        return movieTrailersANDReviews.size();
    }

    @Override
    public Object getItem(int position) {
        return movieTrailersANDReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflaterMovieTrailersReviews = LayoutInflater.from(mContext);
        View viewMovieTrailersReviews = null;

        if (movieTrailersANDReviews.get(position).getType() == "Trailer") {

            viewMovieTrailersReviews = inflaterMovieTrailersReviews.inflate(R.layout.movie_trailers, parent, false);

            //ImageView movieTrailerImageView = (ImageView) viewMovieTrailersReviews.findViewById(R.id.movieTrailerIC);
            //movieTrailerImageView.setImageResource(R.drawable.play_trailer_ic);

            TextView movieTrailerTextView = (TextView) viewMovieTrailersReviews.findViewById(R.id.movieTrailerName);
            movieTrailerTextView.setText(movieTrailersANDReviews.get(position).getMovie_TrailerName_ReviewAuthorName());
        }

        if (movieTrailersANDReviews.get(position).getType() == "Review") {

            viewMovieTrailersReviews = inflaterMovieTrailersReviews.inflate(R.layout.movie_review, parent, false);

            TextView movieAuthorTextView = (TextView) viewMovieTrailersReviews.findViewById(R.id.movieAuthor);
            movieAuthorTextView.setText(movieTrailersANDReviews.get(position).getMovie_TrailerName_ReviewAuthorName());

            TextView movieContentTextView = (TextView) viewMovieTrailersReviews.findViewById(R.id.movieContent);
            movieContentTextView.setText(movieTrailersANDReviews.get(position).getMovie_TrailerLink_ReviewContact());

        }
        return viewMovieTrailersReviews;
    }
}
