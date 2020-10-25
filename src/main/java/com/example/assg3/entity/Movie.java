package com.example.assg3.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int mid;
    @ColumnInfo(name = "movie_name")
    public String movieName;
    @ColumnInfo(name = "releaseDate")
    public String releaseDate;
    @ColumnInfo(name = "date")
    public String addedDate;
    public Movie(String movieName, String releaseDate, String addedDate) {
        this.movieName=movieName;
        this.releaseDate=releaseDate;
        this.addedDate = addedDate;
    }

    public int getId() {
        return mid;
    }

    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getAddedDate() {
        return addedDate;
    }
    public void setAddedDate (String addedDate) {
        this.addedDate = addedDate;
    }
}

