package com.example.home.movies.detailsScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.movies.R;

import java.util.concurrent.ExecutionException;

/**
 * Created by HOME on 12/18/2015.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        Intent intent = getIntent();

        String movieId = intent.getStringExtra("Id");
        String movieName = intent.getStringExtra("Name");
        String movieImage = intent.getStringExtra("Image");
        String movieReleaseDate = intent.getStringExtra("Release_Date");
        String movieOverview = intent.getStringExtra("OverView");
        String movieRate = intent.getStringExtra("Rate");

        MovieDetailsFragment movieFragment = (MovieDetailsFragment) getFragmentManager().findFragmentById(R.id.movieDetailsContainer);
        try {
            if (movieFragment!= null)
                movieFragment.getMovieInfo(movieId, movieName, movieImage, movieReleaseDate, movieOverview, movieRate);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}