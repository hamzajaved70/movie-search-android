package com.example.assg3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenMovie {
    private String movieName;
    private String releaseDate;
    private Double rating;
    
    public HomeScreenMovie(String movieName, String releaseDate, Double rating){
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }
    
    public String getMovieName(){
        return movieName;
    }
    
    public String getReleaseDate(){
        return releaseDate;
    }
    
    public Double getRating(){
        return rating;
    }
    
    public static List<HomeScreenMovie> createHomeScreenMovieList(JSONArray arrayList) throws JSONException {
        List<HomeScreenMovie> list = new ArrayList<HomeScreenMovie>();
        for (int i = 0; i < arrayList.length(); i++)
        {
            JSONObject jsonObj = arrayList.getJSONObject(i);
            list.add(new HomeScreenMovie(((String)jsonObj.get("movieName")), (String)jsonObj.get("releaseDate"), (Double)jsonObj.get("userRating")));
        }
        return list;
    }
}
