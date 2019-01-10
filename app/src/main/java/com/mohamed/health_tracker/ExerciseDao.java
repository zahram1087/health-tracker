package com.mohamed.health_tracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    List<Exercise> getAll();

    @Query("SELECT * FROM exercise WHERE uid IN (:userIds)")
    List<Exercise> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM exercise WHERE activity_title LIKE :first LIMIT 1")
    Exercise findByTitle(String first);

    @Query("SELECT * FROM exercise WHERE quantity LIKE :first LIMIT 1")
    Exercise findByQuantity(int first);

    @Query("SELECT * FROM exercise WHERE timeStamp LIKE :first LIMIT 1")
    Exercise findByTimeStamp(long first);

    @Query("SELECT * FROM exercise WHERE activity_description LIKE :first LIMIT 1")
    Exercise findByActivityDescription(String first);

    @Insert
    void insertAll(Exercise... exercises);

    @Delete
    void delete(Exercise exercise);
}
