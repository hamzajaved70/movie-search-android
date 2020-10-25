package com.example.assg3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assg3.HomeScreenMovie;
import com.example.assg3.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder should contain variables for all the views in each row of the list
        public TextView movieNameTextView;
        public TextView releaseDateTextView;
        public TextView ratingTextView;
        // a constructor that accepts the entire View (itemView)
        // provides a reference and access to all the views in each row
        public ViewHolder(View movieView) {
            super(movieView);
            movieNameTextView = movieView.findViewById(R.id.movie_name);
            releaseDateTextView = movieView.findViewById(R.id.release_date);
            ratingTextView = movieView.findViewById(R.id.rating);
        }
    }
    @Override
    public int getItemCount() {
        return movieList.size();
    }
    private List<HomeScreenMovie> movieList;
    // Pass in the contact array into the constructor
    public RecyclerViewAdapter(List<HomeScreenMovie> list) {
        movieList = list;
    }
    public void addMovies(List<HomeScreenMovie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }
    //This method creates a new view holder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the view from an XML layout file
        View unitsView = inflater.inflate(R.layout.rv_layout_home_screen, parent, false);
        // construct the viewholder with the new view
        ViewHolder viewHolder = new ViewHolder(unitsView);
        return viewHolder;
    }
    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        final HomeScreenMovie movie = movieList.get(position);
        // viewholder binding with its data at the specified position
        TextView movieName= viewHolder.movieNameTextView;
        movieName.setText(movie.getMovieName());
        TextView releaseDate = viewHolder.releaseDateTextView;
        releaseDate.setText(movie.getReleaseDate());
        TextView ratingView = viewHolder.ratingTextView;
        ratingView.setText(Double.toString(movie.getRating()));
    }
}

