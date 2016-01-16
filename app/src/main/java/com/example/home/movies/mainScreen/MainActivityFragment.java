package com.example.home.movies.mainScreen;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.home.movies.MovieAttributes;
import com.example.home.movies.R;
import com.example.home.movies.SettingsActivity;
import com.example.home.movies.getMovieInformation.GetMovieMainDetails;
import com.example.home.movies.movieDatabaseSqlite.MovieDbHelper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by HOME on 12/18/2015.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    ArrayList<MovieAttributes> allMovies = new ArrayList<MovieAttributes>();
    MainActivityFragmentAdapter imageAdapter;
    GridView gridview;

    //----------------------------------------------------------------------------------------------
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
        //Toast.makeText(getActivity(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Toast.makeText(getActivity(), "Refresh Movie List", Toast.LENGTH_SHORT).show();
            try {
                updateMovie();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this.getActivity(), SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_activity_fragment, container, false);

        gridview = (GridView) rootView.findViewById(R.id.mainActivityFragment_GridView);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (allMovies.size() > 0) {

                    try {
                        MovieAttributes movie = allMovies.get(position);
                        com.response(false , movie.getId(), movie.getTitle(),
                                movie.getPosterPath(), movie.getReleaseDate(),
                                movie.getOverview(), movie.getAverageRate());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /*Intent movieDetails = new Intent(getActivity(), MovieDetailsActivity.class);
                    movieDetails.putExtra("Id", allMovies.get(position).getId());
                    movieDetails.putExtra("Name", allMovies.get(position).getTitle());
                    movieDetails.putExtra("Image", allMovies.get(position).getPosterPath());
                    movieDetails.putExtra("Release_Date", allMovies.get(position).getReleaseDate());
                    movieDetails.putExtra("OverView", allMovies.get(position).getOverview());
                    movieDetails.putExtra("Rate", allMovies.get(position).getAverageRate());
                    startActivity(movieDetails);*/

                }
                if (allMovies.size() <= 0) {
                    Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    //----------------------------------------------------------------------------------------------------------------
    public boolean checkInternetState() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    //----------------------------------------------------------------------------------------------------------------
    private void updateMovie() throws ExecutionException, InterruptedException {

        boolean internetState = checkInternetState();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movieSort = sharedPref.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_pop));
        GetMovieMainDetails movieTask = new GetMovieMainDetails();

        if (internetState == false) {
            Toast.makeText(getActivity(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
        }

        if (internetState == true) {

            if (movieSort.equals("favouritesMovie")) {

                allMovies.clear();
                gridview.setAdapter(null);

                MovieDbHelper myDb = new MovieDbHelper(getActivity());
                Cursor res = myDb.getAllData();

                if (res.getCount() == 0) {
                    // show message
                    com.response(true, null, null, null, null, null, null);
                    Toast.makeText(getActivity(), "NO FAVOURITE MOVIES!", Toast.LENGTH_SHORT).show();
                }

                while (res.moveToNext()) {
                    allMovies.add(new MovieAttributes(res.getString(0), res.getString(2), res.getString(1),
                            res.getString(3), res.getString(4), res.getString(5)));

                    MovieAttributes movie = allMovies.get(0);
                    com.response(true, movie.getId(), movie.getTitle(),
                            movie.getPosterPath(), movie.getReleaseDate(),
                            movie.getOverview(), movie.getAverageRate());
                }

                gridview.setAdapter(imageAdapter = new MainActivityFragmentAdapter(getActivity(), allMovies));

                /*Toast.makeText(getActivity(), allMovies.get(0).getId(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), allMovies.get(0).getPosterPath(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), allMovies.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), allMovies.get(0).getOverview(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), allMovies.get(0).getAverageRate(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), allMovies.get(0).getReleaseDate(), Toast.LENGTH_SHORT).show();*/
                //Toast.makeText(getActivity(), Integer.toString(allMovies.size()), Toast.LENGTH_SHORT).show();

            } else {
                try {
                    allMovies.clear();
                    gridview.setAdapter(null);
                    allMovies = movieTask.execute(movieSort).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                MovieAttributes movie = allMovies.get(0);
                com.response(true, movie.getId(), movie.getTitle(),
                        movie.getPosterPath(), movie.getReleaseDate(),
                        movie.getOverview(), movie.getAverageRate());
                //gridview.setAdapter(new MainActivityFragmentAdapter(getActivity(), allMovies));
                gridview.setAdapter(imageAdapter = new MainActivityFragmentAdapter(getActivity(), allMovies));
            }
        }

    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getActivity(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "onResume", Toast.LENGTH_SHORT).show();
        try {
            updateMovie();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(), "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getActivity(), "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getActivity(), "onDestroy", Toast.LENGTH_SHORT).show();
    }
    //----------------------------------------------------------------------------------------------

    Commenecation com;

    public void setCom(Commenecation com) {
        this.com = com;
    }

    public interface Commenecation {
        public void response(Boolean isFirstTime, String id, String name, String image, String releaseDate, String overview, String rate) throws ExecutionException, InterruptedException;
    }
}

