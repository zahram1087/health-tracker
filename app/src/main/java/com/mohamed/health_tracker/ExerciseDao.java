package com.mohamed.health_tracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {
    // Gets all excercises in the database
    @Query("SELECT * FROM exercise")
    List<Exercise> getAllexcercise();

    @Query("SELECT * FROM exercise WHERE id=:id")
    Exercise getById(long id);

    @Insert
    void insertAll(Exercise exercises);

    @Delete
    void delete(Exercise exercise);

    @Update
    void update(Exercise exercise);

}
