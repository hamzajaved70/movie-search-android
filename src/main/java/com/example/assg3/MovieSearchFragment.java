package com.example.assg3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assg3.adapter.RecyclerViewAdapter;
import com.example.assg3.adapter.RecyclerViewAdapterSearch;
import com.example.assg3.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchFragment extends Fragment {
    NetworkConnection networkConnection = null;
    private TextView textView_title_movie_search;
    private TextView textView_details_movie_search;
    private EditText editText_search;
    private Button button_search_movie;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView movieName, releaseDate, rating;
    private List<SearchScreenMovie> movies;
    private RecyclerViewAdapterSearch adapter;

    public MovieSearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.movie_search_fragment, container, false);
        networkConnection = new NetworkConnection();
        //initialize xml variables
        //textView=view.findViewById(R.id.title_movie_search);
        //textView.setText("This is Movie Search");
        recyclerView = view.findViewById(R.id.recyclerView_search);

        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("Message", Context.MODE_PRIVATE);
        String message= sharedPref.getString("message",null);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText_search = view.findViewById(R.id.editText_search);
        button_search_movie = view.findViewById(R.id.button_search_movie);
        recyclerView = view.findViewById(R.id.recyclerView_search);
        button_search_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMovieTitle searchMovieTitle = new SearchMovieTitle();
                searchMovieTitle.execute(editText_search.getText().toString());
            }
        });
    }

    private class SearchMovieTitle extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String movieTitle = params[0].toString();
            return networkConnection.searchMovie(movieTitle);
        }

        @Override
        protected void onPostExecute(String record) {
            try {
                JSONObject json = new JSONObject(record);
                String results = json.get("results").toString();
                JSONArray jsonArray = new JSONArray(results);
                movies=new ArrayList<SearchScreenMovie>();
                try {
                    movies = SearchScreenMovie.createSearchScreenMovieList(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movieName = getView().findViewById(R.id.movie_name);
                releaseDate = getView().findViewById(R.id.release_date);
                rating = getView().findViewById(R.id.rating);
                adapter = new RecyclerViewAdapterSearch(movies);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
