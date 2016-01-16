package com.example.home.movies.mainScreen;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.movies.R;
import com.example.home.movies.detailsScreen.MovieDetailsActivity;
import com.example.home.movies.detailsScreen.MovieDetailsFragment;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements MainActivityFragment.Commenecation {

    MainActivityFragment mainFragment;
    MovieDetailsFragment movieFragment;
    FragmentManager fragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fragManager = getFragmentManager();
        mainFragment = (MainActivityFragment) fragManager.findFragmentById(R.id.mainActivityContainer);
        mainFragment.setCom(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void response(Boolean isFirstTime, String id, String name, String image, String releaseDate, String overview, String rate) throws ExecutionException, InterruptedException {
        movieFragment = (MovieDetailsFragment) fragManager.findFragmentById(R.id.movieDetailsContainer);

        if (movieFragment != null) {
            movieFragment.getMovieInfo(id, name, image, releaseDate, overview, rate);

        } else if(!isFirstTime){
            Intent movieInfo = new Intent(this, MovieDetailsActivity.class);
            movieInfo.putExtra("Id", id);
            movieInfo.putExtra("Name", name);
            movieInfo.putExtra("Image", image);
            movieInfo.putExtra("Release_Date", releaseDate);
            movieInfo.putExtra("OverView", overview);
            movieInfo.putExtra("Rate", rate);
            startActivity(movieInfo);

        }
    }
}
