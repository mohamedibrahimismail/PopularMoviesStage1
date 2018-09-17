package com.example.miestro.popularmoviesstage1.Utilities;


import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    static int id;
    static String original_title;
    static String poster_path;
    static String overview;
    static String release_date;
    static String vote_average;

    public static List<Movie> parseMovieJson(String json) {

        ArrayList<Movie> list_movie = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");


            for(int i=0;i<results.length();i++) {

                JSONObject object = results.getJSONObject(i);
                id = object.getInt("id");
                original_title = object.getString("title");
                poster_path = object.getString("poster_path");
                overview = object.getString("overview");
                vote_average = object.getString("vote_average");
                release_date = object.getString("release_date");

                Movie movie = new Movie(id,original_title,"http://image.tmdb.org/t/p/w185/"+poster_path,overview,vote_average,release_date);

                list_movie.add(movie);

                }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JsonUtils",e.getMessage());
        }


        return list_movie;
    }


}
