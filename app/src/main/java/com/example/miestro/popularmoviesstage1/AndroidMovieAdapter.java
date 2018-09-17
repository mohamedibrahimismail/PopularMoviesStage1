package com.example.miestro.popularmoviesstage1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miestro.popularmoviesstage1.Utilities.Movie;
import com.squareup.picasso.Picasso;

import java.net.InterfaceAddress;
import java.util.List;

public class AndroidMovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = AndroidMovieAdapter.class.getSimpleName();
    private Activity context;
    List<Movie> androidMovies;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param androidMovies List of AndroidFlavor objects to display in a list
     */




    public AndroidMovieAdapter(Activity context, List<Movie> androidMovies) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, androidMovies);
        this.androidMovies=androidMovies;
        this.context=context;

    }




    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Gets the movie object from the ArrayAdapter at the appropriate position
        final Movie movie = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);

        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_image);

        Picasso.with(context).load(movie.getPoster_path()).placeholder(R.drawable.ic_imageholder).into(iconView);
      //  iconView.setImageResource(androidFlavor.image);



        return convertView;
    }



}
