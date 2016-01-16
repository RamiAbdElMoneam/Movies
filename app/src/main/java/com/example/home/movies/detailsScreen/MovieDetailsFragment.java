package com.example.home.movies.detailsScreen;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.movies.MovieAttributes;
import com.example.home.movies.R;
import com.example.home.movies.getMovieInformation.GetMovieReviews;
import com.example.home.movies.getMovieInformation.GetMovieTrailers;
import com.example.home.movies.movieDatabaseSqlite.MovieDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Windows 7 on 1/11/2016.
 */

public class MovieDetailsFragment extends Fragment {

    private final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    //Intent intent;

    ArrayList<MovieAttributes> movieInformation = new ArrayList<MovieAttributes>();
    ArrayList<MovieAttributes> reviewsANDTrailers = new ArrayList<MovieAttributes>();
    private ImageView movieImage;
    private TextView movieName;
    private TextView movieDate;
    private TextView movieOverview;
    private TextView movieRate;
    ImageView addMovieToFavourite;

    //-----------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*intent = getActivity().getIntent();
        String movieId = intent.getStringExtra("Id");
        try {

            reviewsANDTrailers.addAll(new GetMovieTrailers().execute(movieId).get());
            reviewsANDTrailers.addAll(new GetMovieReviews().execute(movieId).get());

            /*for (int i = 0; i < reviewsANDTrailers.size(); i++) {
                Log.v(LOG_TAG, "Movie reviewsANDTrailers: " + reviewsANDTrailers.get(i).getType());
                Log.v(LOG_TAG, "Movie reviewsANDTrailers: " + reviewsANDTrailers.get(i).getMovie_TrailerName_ReviewAuthorName());
                Log.v(LOG_TAG, "Movie reviewsANDTrailers: " + reviewsANDTrailers.get(i).getMovie_TrailerLink_ReviewContact());
            }
            Log.v(LOG_TAG, "Movie reviewsANDTrailers Size: " + reviewsANDTrailers.size());
            //--------------------------------------------------------------------------------------
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }

    //-----------------------------------------------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movie_details_fragment, container, false);

        ListView movieDetailsList = (ListView) rootView.findViewById(R.id.movie_details_list_view);

        movieDetailsList.addHeaderView(GetHeader(), null, false);

        movieDetailsList.setAdapter(new MovieDetailsFragmentAdapter(getActivity(), reviewsANDTrailers));
        //------------------------------------------------------------------------------------------
        movieDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (reviewsANDTrailers.get(position - 1).getType() == "Trailer") {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(reviewsANDTrailers.get(position - 1).getMovie_TrailerLink_ReviewContact())));
                }
            }
        });
        return rootView;
    }


    //-----------------------------------------------------------------------------------------------------------------------------------
    private View GetHeader() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View header = inflater.inflate(R.layout.movie_details, null, false);
        //******************************************************************************************

        movieImage = (ImageView) header.findViewById(R.id.movieImage);
        movieName = (TextView) header.findViewById(R.id.movieName);
        movieDate = (TextView) header.findViewById(R.id.movieDate);
        movieOverview = (TextView) header.findViewById(R.id.movieOverview);
        movieRate = (TextView) header.findViewById(R.id.movieRate);
        addMovieToFavourite = (ImageView) header.findViewById(R.id.toggleButton);

        //******************************************************************************************

        return header;
    }
    //-----------------------------------------------------------------------------------------------------------------------------------

    public void getMovieInfo(String id, String name, String image, String releaseDate, String overview, String rate) throws ExecutionException, InterruptedException {

        movieInformation.clear();
        reviewsANDTrailers.clear();

        if (id != null){
            movieInformation.add(new MovieAttributes(id, image, name, overview, rate, releaseDate));
            reviewsANDTrailers.addAll(new GetMovieTrailers().execute(id).get());
            reviewsANDTrailers.addAll(new GetMovieReviews().execute(id).get());

            Picasso.with(getActivity()).load(movieInformation.get(0).getPosterPath()).into(movieImage);
            movieName.setText(movieInformation.get(0).getTitle());
            movieDate.setText(movieInformation.get(0).getReleaseDate());
            movieRate.setText(movieInformation.get(0).getAverageRate());
            movieOverview.setText(movieInformation.get(0).getOverview());

            MovieDbHelper myDb = new MovieDbHelper(getActivity());
            //int check = myDb.checkData(intent.getStringExtra("Id"));
            int check = myDb.checkData(movieInformation.get(0).getId());
            if (check == 1) {
                addMovieToFavourite.setBackgroundResource(R.drawable.star_on_ic);
            }
            if (check == 0) {
                addMovieToFavourite.setBackgroundResource(R.drawable.star_off_ic);
            }
            addMovieToFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MovieDbHelper myDb = new MovieDbHelper(getActivity());
                    int check = myDb.checkData(movieInformation.get(0).getId());

                    if (check == 0) {
                        boolean isInserted = myDb.insertData(movieInformation.get(0).getId(), movieInformation.get(0).getPosterPath(),
                                movieInformation.get(0).getTitle(), movieInformation.get(0).getOverview(),
                                movieInformation.get(0).getAverageRate(), movieInformation.get(0).getReleaseDate());

                        if (isInserted == true) {
                            addMovieToFavourite.setBackgroundResource(R.drawable.star_on_ic);
                            Toast.makeText(getActivity(), "Favourite", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();

                    }

                    if (check == 1) {
                        Integer deletedrows = myDb.deleteData(movieInformation.get(0).getId());
                        if (deletedrows > 0) {
                            addMovieToFavourite.setBackgroundResource(R.drawable.star_off_ic);
                            Toast.makeText(getActivity(), "UnFavourite", Toast.LENGTH_LONG).show();

                        } else
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


    }
    //-----------------------------------------------------------------------------------------------------------------------------------
}
