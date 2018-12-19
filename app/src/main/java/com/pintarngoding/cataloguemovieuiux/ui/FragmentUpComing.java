package com.pintarngoding.cataloguemovieuiux.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pintarngoding.cataloguemovieuiux.MainActivity;
import com.pintarngoding.cataloguemovieuiux.R;
import com.pintarngoding.cataloguemovieuiux.adapter.MovieAdapter;
import com.pintarngoding.cataloguemovieuiux.loader.MovieAsyncTaskLoader;
import com.pintarngoding.cataloguemovieuiux.model.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUpComing extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    RecyclerView recyclerViewUpComing;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private MovieAdapter movieAdapter;

    public FragmentUpComing() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_up_coming, container, false);

        ((MainActivity)getActivity()).setActionBarTitle(getResources().getString(R.string.movieupcoming));

        recyclerViewUpComing = view.findViewById(R.id.recyclerView_upcoming);
        recyclerViewUpComing.setHasFixedSize(true);

        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();

        recyclerViewUpComing.setAdapter(movieAdapter);
        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(0, bundle, this);

        if (savedInstanceState != null){
            movieArrayList = savedInstanceState.getParcelableArrayList("STATE_UPCOMING");
            movieAdapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("STATE_UPCOMING", movieArrayList);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MovieAsyncTaskLoader(getContext(), "upcoming");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        movieAdapter.setData(movies);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        movieAdapter.setData(null);
    }
}
