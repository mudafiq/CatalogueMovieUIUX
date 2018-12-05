package com.pintarngoding.cataloguemovieuiux.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pintarngoding.cataloguemovieuiux.R;
import com.pintarngoding.cataloguemovieuiux.model.Movie;
import com.pintarngoding.cataloguemovieuiux.ui.MovieDetailActivity;
import com.pintarngoding.cataloguemovieuiux.ui.MovieSearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setData(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_items, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MyViewHolder myViewHolder, int i) {
        final Movie movie = getMovieArrayList().get(i);

        myViewHolder.textViewTitle.setText(movie.getTitle());
        myViewHolder.textViewDesc.setText(movie.getDescription());
        myViewHolder.textViewReleaseDate.setText(movie.getRelease_date());
        Picasso.get().load(movie.getPoster())
                .error(R.drawable.image_not_available).into(myViewHolder.imageViewPoster);
        myViewHolder.buttonDetail.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intentDetail = new Intent(context, MovieDetailActivity.class);
                intentDetail.putExtra("EXTRA_MOVIE_DETAIL", movie);
                context.startActivity(intentDetail);
            }
        }));
        myViewHolder.buttonShare.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, movie.getTitle() + "\n\n" + movie.getDescription());
                context.startActivity(Intent.createChooser(share, "Share Movie"));
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getMovieArrayList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPoster;
        TextView textViewTitle, textViewDesc, textViewReleaseDate;
        Button buttonDetail, buttonShare;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewNowPlayingTitle);
            textViewDesc = itemView.findViewById(R.id.textViewNowPlayingDesc);
            textViewReleaseDate = itemView.findViewById(R.id.textViewNowPlayingTanggal);
            imageViewPoster = itemView.findViewById(R.id.imageViewNowPlaying);
            buttonDetail = itemView.findViewById(R.id.buttonNowPlayingDetail);
            buttonShare = itemView.findViewById(R.id.buttonNowPlayingShare);
        }
    }
}
