package com.example.assg3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MovieMemoirFragment extends Fragment {
    public MovieMemoirFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.movie_memoir_fragment, container, false);

        //initialize xml variables

        SharedPreferences sharedPref= getActivity().
                getSharedPreferences("Message", Context.MODE_PRIVATE);
        String message= sharedPref.getString("message",null);

        return view;
    }
}
