package com.pintarngoding.cataloguemovieuiux.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pintarngoding.cataloguemovieuiux.R;
import com.pintarngoding.cataloguemovieuiux.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewBackdrop;
    TextView textViewTitle, textViewRating, textViewDate, textViewDesc;
    Button buttonShare;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DETAIL CATALOGUE MOVIE");

        imageViewBackdrop = findViewById(R.id.imageViewBackdrop);
        textViewTitle = findViewById(R.id.textViewDetailTitle);
        textViewRating = findViewById(R.id.textViewDetailRating);
        textViewDate = findViewById(R.id.textViewDetailReleaseDate);
        textViewDesc = findViewById(R.id.textViewDetailDesc);
        buttonShare = findViewById(R.id.buttonShare);

        movie = getIntent().getParcelableExtra("EXTRA_MOVIE_DETAIL");
        Picasso.get().load(movie.getBackdrop()).error(R.drawable.image_not_available_landscape).into(imageViewBackdrop);
        textViewTitle.setText(movie.getTitle());
        textViewRating.setText(movie.getRating());
        textViewDate.setText(movie.getRelease_date());
        textViewDesc.setText(movie.getDescription());

        buttonShare.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, movie.getTitle() + "\n\n" + movie.getDescription());
        startActivity(Intent.createChooser(share, "Share Movie"));
    }
}
