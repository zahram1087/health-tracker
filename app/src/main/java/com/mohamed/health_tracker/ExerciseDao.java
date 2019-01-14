package com.mohamed.health_tracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
