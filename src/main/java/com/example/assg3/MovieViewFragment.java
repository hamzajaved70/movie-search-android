package com.example.assg3;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assg3.adapter.RecyclerViewAdapterSearch;
import com.example.assg3.database.MovieDatabase;
import com.example.assg3.entity.Movie;
import com.example.assg3.networkconnection.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieViewFragment extends Fragment {
    NetworkConnection networkConnection = null;
    MovieDatabase db = null;

    public MovieViewFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.movie_view_fragment, container, false);
        networkConnection = new NetworkConnection();
        //initialize xml variables

        SharedPreferences sharedPref= getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String message= sharedPref.getString("message",null);
        System.out.println("ID: " + message);
        SearchMovieTitle searchMovieTitle = new SearchMovieTitle();
        searchMovieTitle.execute(message);

        SearchMovieCast searchMovieCast = new SearchMovieCast();
        searchMovieCast.execute(message);
        return view;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        db = MovieDatabase.getInstance(getContext());
        Button addToWatchList = getActivity().findViewById(R.id.button_add_to_watchlist);
        addToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDatabase addDatabase = new InsertDatabase();
                TextView name = getActivity().findViewById(R.id.textView_movie_title);
                TextView date = getActivity().findViewById(R.id.textView_release_date);
                String [] scripts = new String [] {name.getText().toString(),date.getText().toString()};
                addDatabase.execute(scripts);

            }
        });

    }

    private class InsertDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            Movie movie = new Movie(params[0], params[1], dateobj.toString());
            long id = db.customerDao().insert(movie);
            return (id + " " + params[0] + " " + params[1] + " " + dateobj);
        }
        @Override
        protected void onPostExecute(String details) {
            Toast.makeText(getContext(), "Added Record: " + details, Toast.LENGTH_SHORT).show();
        }
    }
    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Movie> movies = db.customerDao().getAll();
            if (!(movies.isEmpty() || movies == null) ){
                String allMovies = "";
                for (Movie temp : movies) {
                    String moviestr = (temp.getId() + " " + temp.getMovieName() +
                            " " + temp.getReleaseDate() + " "+ temp.getAddedDate()+ " , " );
                    allMovies = allMovies + System.getProperty("line.separator") + moviestr;
                }
                return allMovies;
            }
            else
                return "";
        }
        @Override
        protected void onPostExecute(String details) {
            Toast.makeText(getContext(), "All data: " + details, Toast.LENGTH_SHORT).show();
        }
    }

    private class SearchMovieTitle extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Integer movieTitle = Integer.parseInt(params[0]);
            return networkConnection.searchMovieById(movieTitle);
        }

        @Override
        protected void onPostExecute(String record) {
            try {
                System.out.println(record);
                JSONObject json = new JSONObject(record);
                String results = json.get("genres").toString();
                //JSONArray jsonArray = new JSONArray(results);
                // the genre, the cast, the release date, the country, the name of the director(s), a synopsis/plot
                //summary/storyline
                String resultsGenres = json.get("genres").toString();
                JSONArray jsonArrayGenres = new JSONArray(resultsGenres);
                String genre = "Genre: ";
                for (int i = 0; i < jsonArrayGenres.length(); i++)
                {
                    JSONObject jsonObj = jsonArrayGenres.getJSONObject(i);
                    System.out.println("Genre: " + jsonObj.get("name").toString());
                    genre = genre +jsonObj.get("name").toString();
                }

                TextView title = getActivity().findViewById(R.id.textView_movie_title);
                title.setText("Movie Title: " + json.get("original_title").toString());
                TextView genreT = getActivity().findViewById(R.id.textView_genre);
                genreT.setText(genre);
                //System.out.println(json.get("genres").toString());
                System.out.println("Release Date: " + json.get("release_date").toString());
                TextView release = getActivity().findViewById(R.id.textView_release_date);
                release.setText("Release Date: " + json.get("release_date").toString());
                String resultsCountry = json.get("production_countries").toString();
                JSONArray jsonArrayCountries = new JSONArray(resultsCountry);
                for (int i = 0; i < jsonArrayCountries.length(); i++)
                {
                    JSONObject jsonObj = jsonArrayCountries.getJSONObject(i);
                    System.out.println("Country: " + jsonObj.get("name").toString());
                    TextView country = getActivity().findViewById(R.id.textView_country);
                    release.setText("Country: " + jsonObj.get("name").toString());
                }
                Float vote = Float.parseFloat(json.get("vote_average").toString());
                vote = (vote / 10) * 5;
                RatingBar ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar);
                ratingBar.setRating(vote);
                //System.out.println(json.get("production_countries").toString());
                //System.out.println("Synopsis: " + json.get("overview").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class SearchMovieCast extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Integer movieTitle = Integer.parseInt(params[0]);
            return networkConnection.searchCastById(movieTitle);
        }

        @Override
        protected void onPostExecute(String record) {
            try {
                //System.out.println(record);
                JSONObject json = new JSONObject(record);
                String results = json.get("crew").toString();
                JSONArray jsonArray = new JSONArray(results);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String director = jsonObj.get("job").toString();
                    if (director.equals("Director")){
                        System.out.println("Director: " + jsonObj.get("name").toString());
                        TextView directorT = getActivity().findViewById(R.id.textView_director);
                        directorT.setText("Director: " + jsonObj.get("name").toString());
                    }
                }

                String resultsCast = json.get("cast").toString();
                JSONArray jsonArrayCast = new JSONArray(resultsCast);
                for (int i = 0; i < 5; i++)
                {
                    JSONObject jsonObj = jsonArrayCast.getJSONObject(i);
                    System.out.println("Character: " + jsonObj.get("character").toString() + " Actor: " + jsonObj.get("name").toString());
                    if (i == 0){
                        TextView cast = getActivity().findViewById(R.id.textView_cast1);
                        cast.setText("Character: " + jsonObj.get("character").toString() + " Actor: " + jsonObj.get("name").toString());
                    }
                    if (i == 1){
                        TextView cast = getActivity().findViewById(R.id.textView_cast2);
                        cast.setText("Character: " + jsonObj.get("character").toString() + " Actor: " + jsonObj.get("name").toString());
                    }
                    if (i == 2){
                        TextView cast = getActivity().findViewById(R.id.textView_cast3);
                        cast.setText("Character: " + jsonObj.get("character").toString() + " Actor: " + jsonObj.get("name").toString());
                    }
                    if (i == 3){
                        TextView cast = getActivity().findViewById(R.id.textView_cast4);
                        cast.setText("Character: " + jsonObj.get("character").toString() + " Actor: " + jsonObj.get("name").toString());
                    }
                    if (i == 4){
                        TextView cast = getActivity().findViewById(R.id.textView_cast5);
                        cast.setText("Character: " + jsonObj.get("character").toString() + " Actor: " + jsonObj.get("name").toString());
                    }
                }
                // the genre, the cast, the release date, the country, the name of the director(s), a synopsis/plot
                //summary/storyline
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}