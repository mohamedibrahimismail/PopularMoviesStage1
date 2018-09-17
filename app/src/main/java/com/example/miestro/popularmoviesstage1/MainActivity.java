package com.example.miestro.popularmoviesstage1;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miestro.popularmoviesstage1.Utilities.JsonUtils;
import com.example.miestro.popularmoviesstage1.Utilities.Movie;
import com.example.miestro.popularmoviesstage1.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ActionBarActivity {

    @BindView(R.id.pb_loading_indicator)   ProgressBar mLoadingIndicator;
    @BindView(R.id.movie_grid)  GridView gridView;
    AndroidMovieAdapter androidMovieAdapter;
    String default_sortedmovie = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        GetMovieData(default_sortedmovie);


    }

    public void GetMovieData(String sorted_by){
        URL url=NetworkUtils.buildUrl(sorted_by);

        if(Network_Connected()) {
            new GithubQueryTask().execute(url);
        }else{
            Toast.makeText(MainActivity.this,"Please Check Network Connection",Toast.LENGTH_LONG).show();
        }
    }



    public boolean Network_Connected(){

        boolean connected =false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);

        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()== NetworkInfo.State.CONNECTED ||
             connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()==NetworkInfo.State.CONNECTED    ){
            connected = true;
        }

        return connected;
    }



    public class GithubQueryTask extends AsyncTask<URL, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String Results = null;
            try {
                Results = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Results;
        }

        @Override
        protected void onPostExecute(String Results) {

            mLoadingIndicator.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            if (Results != null && !Results.equals("")) {
                      handleGridview(JsonUtils.parseMovieJson(Results));
            }
        }


    }


    public void handleGridview(final List<Movie> list){
        androidMovieAdapter=new AndroidMovieAdapter(this,list);
        gridView.setAdapter(androidMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,list.get(position).getOriginal_title(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Detailes.class);
                intent.putExtra("title",list.get(position).getOriginal_title());
                intent.putExtra("poster",list.get(position).getPoster_path());
                intent.putExtra("release_date",list.get(position).getRelease_date());
                intent.putExtra("rate",list.get(position).getVote_average());
                intent.putExtra("overview",list.get(position).getOverview());

                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.most_popular){
            GetMovieData("popular");
            return true;
        }else if (id==R.id.top_rated){
            GetMovieData("top_rated");
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
