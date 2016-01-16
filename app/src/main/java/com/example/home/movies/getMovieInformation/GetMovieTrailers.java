package com.example.home.movies.getMovieInformation;

import android.os.AsyncTask;
import android.util.Log;

import com.example.home.movies.MovieAttributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Windows 7 on 12/26/2015.
 */
public class GetMovieTrailers extends AsyncTask<String, Void, ArrayList> {

    private final String LOG_TAG = GetMovieTrailers.class.getSimpleName();

    //------------------------------------------------------------------------------------------
    private ArrayList getMovieDataFromJson(String moviesJsonStr)
            throws JSONException {

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");

        ArrayList<MovieAttributes> movieTrailers = new ArrayList<MovieAttributes>();

        for (int i = 0; i < moviesArray.length(); i++) {

            // Get the JSON object representing the movie
            JSONObject movieData = moviesArray.getJSONObject(i);

            movieTrailers.add(new MovieAttributes("Trailer", movieData.getString("name"),
                    "https://www.youtube.com/watch?v=" + movieData.getString("key")));
        }

        /*for (int i = 0; i < movieTrailers.size(); i++) {
            Log.v(LOG_TAG, "MovieKeys" + movieTrailersLinks.get(i));
        }*/

        return movieTrailers;
    }

    //------------------------------------------------------------------------------------------
    @Override
    protected ArrayList doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr = null;

        try {

            String baseUrl = "http://api.themoviedb.org/3/movie/" + params[0] + "/videos?api_key=4e55e51ed8d84ee49380b766fa500b5f";
            URL url = new URL(baseUrl);

            // Create the request to MovieAPI, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the movie movieDatabaseSqlite, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMovieDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    //------------------------------------------------------------------------------------------
    /*@Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
    }*/
    //------------------------------------------------------------------------------------------
}
