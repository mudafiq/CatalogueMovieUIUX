package com.pintarngoding.cataloguemovieuiux.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
public class FragmentNowPlaying extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    RecyclerView recyclerViewNowPlaying;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private MovieAdapter movieAdapter;

    public FragmentNowPlaying() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_now_playing, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Movie Now Playing");

        recyclerViewNowPlaying = view.findViewById(R.id.recyclerView_nowplaying);
        recyclerViewNowPlaying.setHasFixedSize(true);

        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();

        recyclerViewNowPlaying.setAdapter(movieAdapter);
        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(0, bundle, this);

        if (savedInstanceState != null) {
            movieArrayList = savedInstanceState.getParcelableArrayList("STATE_RESULT");
            movieAdapter.notifyDataSetChanged();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("STATE_RESULT", movieArrayList);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle bundle) {
        return new MovieAsyncTaskLoader(getContext(), "now_playing");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        movieAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        movieAdapter.setData(null);
    }
}
