package com.example.assg3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assg3.adapter.RecyclerViewAdapter;
import com.example.assg3.networkconnection.NetworkConnection;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView movieName, releaseDate, rating;
    private List<HomeScreenMovie> movies;
    private RecyclerViewAdapter adapter;

    NetworkConnection networkConnection = null;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        networkConnection = new NetworkConnection();
        dateTimeDisplay=view.findViewById(R.id.textView_date_today);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        recyclerView = view.findViewById(R.id.recyclerView_name);
        SharedPreferences sharedPref= getActivity().getSharedPreferences("user_id", Context.MODE_PRIVATE);
        String message= sharedPref.getString("user_id",null);

        //dateTimeDisplay=view.findViewById(R.id.textView_date_today);
        //dateTimeDisplay.setText("Welcome!");
        GetHomeScreenMovies getHomeScreenMovies = new GetHomeScreenMovies();
        //getHomeScreenMovies.execute(message);
        getHomeScreenMovies.execute(3);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private class GetHomeScreenMovies extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... params) {
            Integer personId = Integer.parseInt(params[0].toString());
            return networkConnection.getHomeScreenMovies(personId);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String memoirs) {
            //TextView detailsTextView = findViewById(R.id.text_details);
            System.out.println(memoirs);
            //textView = getView().findViewById(R.id.tv);
            //textView.setText(memoirs);
            try {
                JSONArray jsonArray = new JSONArray(memoirs);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);

                    System.out.println(jsonObj);
                }
                //JSONObject json = new JSONObject(memoirs.substring(1, memoirs.length() - 1));
                //JSONArray jsonArray = new JSONArray();
                //jsonArray.put(json);
                JSONObject firstMovie = jsonArray.getJSONObject(1);
                //textView.setText((String)firstMovie.get("movieName"));
                //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //detailsTextView.setText(credentials);

            movies=new ArrayList<HomeScreenMovie>();
            try {
                movies = HomeScreenMovie.createHomeScreenMovieList(new JSONArray(memoirs));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            movieName = getView().findViewById(R.id.movie_name);
            releaseDate = getView().findViewById(R.id.release_date);
            rating = getView().findViewById(R.id.rating);
            adapter = new RecyclerViewAdapter(movies);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}

