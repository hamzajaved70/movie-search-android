package com.example.assg3.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assg3.MovieViewFragment;
import com.example.assg3.R;
import com.example.assg3.SearchScreenMovie;

import java.util.List;

public class RecyclerViewAdapterSearch extends RecyclerView.Adapter<RecyclerViewAdapterSearch.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder should contain variables for all the views in each row of the list
        public TextView movieNameTextView;
        public TextView releaseDateTextView;
        public ImageView imageImageView;
        // a constructor that accepts the entire View (itemView)
        // provides a reference and access to all the views in each row
        public ViewHolder(View movieView) {
            super(movieView);
            movieNameTextView = movieView.findViewById(R.id.movie_name);
            releaseDateTextView = movieView.findViewById(R.id.release_year);
            imageImageView = movieView.findViewById(R.id.movie_image);
        }
    }
    @Override
    public int getItemCount() {
        return movieList.size();
    }
    private List<SearchScreenMovie> movieList;
    // Pass in the contact array into the constructor
    public RecyclerViewAdapterSearch(List<SearchScreenMovie> list) {
        movieList = list;
    }
    public void addMovies(List<SearchScreenMovie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }
    //This method creates a new view holder that is constructed with a new View, inflated from a layout
    @Override
    public RecyclerViewAdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the view from an XML layout file
        View unitsView = inflater.inflate(R.layout.rv_layout_search, parent, false);
        // construct the viewholder with the new view
        ViewHolder viewHolder = new ViewHolder(unitsView);
        return viewHolder;
    }
    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterSearch.ViewHolder viewHolder, int position) {
        final SearchScreenMovie movie = movieList.get(position);
        // viewholder binding with its data at the specified position
        TextView movieName= viewHolder.movieNameTextView;
        movieName.setText(movie.getMovieName());
        TextView releaseDate = viewHolder.releaseDateTextView;
        releaseDate.setText(movie.getReleaseDate());

        viewHolder.movieNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieViewFragment mvFragment = new MovieViewFragment();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, mvFragment);
                fragmentTransaction.commit();

                SharedPreferences sharedPref= activity.getSharedPreferences("Message", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sharedPref.edit();
                spEditor.putString("message", Integer.toString(movie.getId()));
                spEditor.apply();
            }
        });

    }
}

