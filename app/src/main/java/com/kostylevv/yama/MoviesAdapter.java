package com.kostylevv.yama;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vkostylev on 14/02/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        Picasso
                .with(mContext)
                .load(movieList.get(position).getPoster())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView poster;

        public MovieViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_movie_title);
            poster = (ImageView) view.findViewById(R.id.iv_poster);
        }

    }

    public void setMoviesData(List<Movie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }

    public MoviesAdapter(Context context) {
        mContext = context;
    }

    private List<Movie> movieList;
    private Context mContext;

}
