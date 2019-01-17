package com.mohamed.health_tracker;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import androidx.room.Room;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;

import static junit.framework.TestCase.*;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {
    //source: https://developer.android.com/training/data-storage/room/testing-db#java
    private ExerciseDao mExcerciseDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mExcerciseDao = mDb.getExerciseDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void createAndReadExerciseTestAddedToDataBase() throws Exception{

        String timestamp = new Date().toString();
        Exercise exercise = new Exercise("jumping jacks", "5", "did jumping jacks", timestamp);
        mExcerciseDao.insertAll(exercise);

        List<Exercise> exercises = mExcerciseDao.getAllexcercise();

        assertFalse(exercise== null);
    }

    @Test
    public void createAndReadExerciseTestAdded() throws Exception{

        String timestamp = new Date().toString();
        Exercise exercise = new Exercise("jumping jacks", "5", "did jumping jacks", timestamp);
        mExcerciseDao.insertAll(exercise);
        Exercise exerciseById = mExcerciseDao.getById(1);
        assertEquals(exercise.title, exerciseById.title);

    }

    @Test
    public void createAndReadExerciseToString() throws Exception{

        String timestamp = new Date().toString();
        Exercise exercise = new Exercise("jumping jacks", "5", "did jumping jacks", timestamp);
        mExcerciseDao.insertAll(exercise);
        Exercise exerciseById = mExcerciseDao.getById(1);
        assertEquals(exercise.toString(), exerciseById.toString());

    }

    @Test
    public void createAndReadExerciseRetrieval() throws Exception{

        String timestamp = new Date().toString();
        Exercise exercise = new Exercise("pull ups", "5", "pull ups", timestamp);

        String timestamp1 = new Date().toString();
        Exercise exercise1 = new Exercise("run", "3 miles", "running", timestamp1);

        String timestamp2 = new Date().toString();
        Exercise exercise2 = new Exercise("jumping jacks", "12", "did jumping jacks", timestamp2);


        mExcerciseDao.insertAll(exercise);
        mExcerciseDao.insertAll(exercise1);
        mExcerciseDao.insertAll(exercise2);

        mExcerciseDao.getLast();

        Exercise exerciseById = mExcerciseDao.getById(3);
        assertEquals(exerciseById.toString(), mExcerciseDao.getLast().toString());

    }


}
