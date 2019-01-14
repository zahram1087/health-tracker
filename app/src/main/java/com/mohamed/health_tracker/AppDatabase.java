package com.mohamed.health_tracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;



@Database(entities = {Exercise.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciseDao getExerciseDao();

}
