package com.pintarngoding.cataloguemovieuiux.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.pintarngoding.cataloguemovieuiux.R;
import com.pintarngoding.cataloguemovieuiux.adapter.MovieAdapter;
import com.pintarngoding.cataloguemovieuiux.loader.MovieSearchAsyncTaskLoader;
import com.pintarngoding.cataloguemovieuiux.model.Movie;

import java.util.ArrayList;

public class MovieSearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    RecyclerView recyclerViewSearch;
    ArrayList<Movie> movieArrayList = new ArrayList<>();
    MovieAdapter movieAdapter;
    String queryCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewSearch = findViewById(R.id.recyclerView_search);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));

        movieAdapter = new MovieAdapter(this);
        movieAdapter.notifyDataSetChanged();
        recyclerViewSearch.setAdapter(movieAdapter);

        queryCari = getIntent().getStringExtra("EXTRA_SEARCH");
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_SEARCH", queryCari);

        getLoaderManager().initLoader(0, bundle, MovieSearchActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (bundle != null) {
            queryCari = bundle.getString("BUNDLE_SEARCH");
        }
        return new MovieSearchAsyncTaskLoader(MovieSearchActivity.this, queryCari);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        movieAdapter.setData(movies);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        movieAdapter.setData(new ArrayList<Movie>());
    }
}
