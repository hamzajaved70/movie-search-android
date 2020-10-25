package com.example.assg3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.assg3.database.MovieDatabase;
import com.example.assg3.entity.Movie;

import java.util.List;

public class WatchListFragment extends Fragment {
    MovieDatabase db = null;
    public WatchListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.watch_list_fragment, container, false);

        //initialize xml variables

        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("Message", Context.MODE_PRIVATE);
        String message= sharedPref.getString("message",null);
        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();
        return view;
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            db = MovieDatabase.getInstance(getContext());
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
            TextView tv = getActivity().findViewById(R.id.textView_watch_list);
            tv.setText("All data: " + details);
            //Toast.makeText(getContext(), "All data: " + details, Toast.LENGTH_SHORT).show();
        }
    }
}
