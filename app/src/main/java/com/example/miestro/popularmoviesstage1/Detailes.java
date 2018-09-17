package com.example.miestro.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Detailes extends AppCompatActivity {

    @BindView(R.id.movie_name) TextView title;
    @BindView(R.id.realse_date) TextView release_date;
    @BindView(R.id.rating) TextView rate;
    @BindView(R.id.overview) TextView overview;
    @BindView(R.id.movie_poster) ImageView poster;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);


        ButterKnife.bind(this);

        Intent intent =getIntent();
        getDataFromIntent(intent);


    }

    public void getDataFromIntent(Intent intent){


        if(intent.hasExtra("title")){
            title.setText(intent.getStringExtra("title"));
        }
        if(intent.hasExtra("poster")){
            Picasso.with(Detailes.this).load(intent.getStringExtra("poster")).placeholder(R.drawable.ic_imageholder).into(poster);

        }
        if(intent.hasExtra("release_date")){
            release_date.setText(intent.getStringExtra("release_date"));
        }
        if(intent.hasExtra("rate")){
            rate.setText(intent.getStringExtra("rate")+"/10");
        }
        if(intent.hasExtra("overview")){
            overview.setText(intent.getStringExtra("overview"));
        }


    }

}
