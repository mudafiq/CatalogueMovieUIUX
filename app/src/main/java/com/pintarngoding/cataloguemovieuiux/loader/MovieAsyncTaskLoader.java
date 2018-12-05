package com.pintarngoding.cataloguemovieuiux.loader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.pintarngoding.cataloguemovieuiux.BuildConfig;
import com.pintarngoding.cataloguemovieuiux.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private ArrayList<Movie> movieArrayList;
    private boolean hasResult = false;
    private static final String API_KEY = BuildConfig.MY_API_KEY;
    private String jenisAPI;

    public MovieAsyncTaskLoader(final Context context, String jenisAPI) {
        super(context);
        this.jenisAPI = jenisAPI;
        onContentChanged();
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> movieArrayList = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/"+jenisAPI+"?api_key="+API_KEY+"&language=en-US&page=1";
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
                        movieArrayList.add(movie);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieArrayList;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(movieArrayList);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Movie> data) {
        movieArrayList = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult){
            onReleaseResources(movieArrayList);
            movieArrayList = null;
            hasResult = false;
        }
    }
    protected void onReleaseResources(ArrayList<Movie> movieArrayList){

    }
}
