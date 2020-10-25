package com.example.assg3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchScreenMovie {
    private String movieName;
    private String releaseDate;
    private Integer id;

    public SearchScreenMovie(String movieName, String releaseDate, Integer id){
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.id = id;
    }

    public String getMovieName(){
        return movieName;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public Integer getId() { return id; }

    //public Double getRating(){
    //    return rating;
    //}

    public static List<SearchScreenMovie> createSearchScreenMovieList(JSONArray arrayList) throws JSONException {
        List<SearchScreenMovie> list = new ArrayList<SearchScreenMovie>();
        for (int i = 0; i < arrayList.length(); i++)
        {
            JSONObject jsonObj = arrayList.getJSONObject(i);
            list.add(new SearchScreenMovie(((String)jsonObj.get("title")), (String)jsonObj.get("release_date"), (Integer) jsonObj.get("id")));
        }
        return list;
    }
}
