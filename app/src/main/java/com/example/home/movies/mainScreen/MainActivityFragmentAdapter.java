package com.example.home.movies.mainScreen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.home.movies.MovieAttributes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HOME on 12/18/2015.
 */
public class MainActivityFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MovieAttributes> moviez =new ArrayList<MovieAttributes>();

    public MainActivityFragmentAdapter(Context c, ArrayList<MovieAttributes> movies) {
        this.mContext = c;
        this.moviez = movies;
    }

    public MainActivityFragmentAdapter(Context c) {
        this.mContext = c;
    }

    public int getCount() {
        return moviez.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {

            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso
                .with(mContext)
                .load(moviez.get(position).getPosterPath())
                .into(imageView);

        return imageView;
    }
}
