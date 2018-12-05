package com.pintarngoding.cataloguemovieuiux.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.pintarngoding.cataloguemovieuiux.BuildConfig;
import com.pintarngoding.cataloguemovieuiux.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieSearchAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> arrayListMovie;
    private boolean hasResult = false;
    private String stringCari;
    private static final String API_KEY = BuildConfig.MY_API_KEY;

    public MovieSearchAsyncTaskLoader(final Context context, String stringCari) {
        super(context);
        onContentChanged();
        this.stringCari = stringCari;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult){
            onReleaseResources(arrayListMovie);
            arrayListMovie = null;
            hasResult = false;
        }
    }

    protected void onReleaseResources(ArrayList<Movie> data){
        //nothing to do
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
        arrayListMovie = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(arrayListMovie);
    }


    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> moviesItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+stringCari;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonMovie = jsonArray.getJSONObject(i);
                        Movie movie = new Movie(jsonMovie);
                        moviesItems.add(movie);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return moviesItems;
    }
}
