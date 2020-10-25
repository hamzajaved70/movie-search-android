package com.example.assg3.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assg3.entity.Movie;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();
    @Query("SELECT * FROM movie WHERE mid = :movieId LIMIT 1")
    Movie findByID(int movieId);
    @Insert
    void insertAll(Movie... movie);
    @Insert
    long insert(Movie movie);
    @Delete
    void delete(Movie movie);
    @Update(onConflict = REPLACE)
    void updateMovies(Movie... movies);
    @Query("DELETE FROM movie")
    void deleteAll();
}

