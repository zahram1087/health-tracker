package com.mohamed.health_tracker;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;


import java.util.Date;

public class ExcerciseDiary extends AppCompatActivity {



    //display the data in the dataBase
    private static Exercise exercise;
    private static AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_diary);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").allowMainThreadQueries().build();

//        //checking database working
//        Exercise testing = new Exercise("testing", "testing","testing", "testing");
//        appDatabase.getExerciseDao().insertAll(testing);

        //source: http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
        recyclerView = (RecyclerView) findViewById(R.id.diaryRecycler);
        recyclerView.setHasFixedSize(true);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // define an adapter
        mAdapter = new MyAdapter(appDatabase.getExerciseDao().getAllexcercise());
        recyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();


    }

    //saving the diary entry to the dataBase

    public void addDiaryEntryOnButtonClick(View view){

        //finding the EditTextby ID:
        EditText editTitle = findViewById(R.id.title);
        EditText editQuantity = findViewById(R.id.quantity);
        EditText editDescription=findViewById(R.id.description);
        String timestamp = new Date().toString();

       
        //textEdit 9: time stamp not adding well

        // fetch data and create Excercise object
        Exercise exercise = new Exercise(editTitle.getText().toString(), editQuantity.getText().toString(), editDescription.getText().toString(), timestamp);
        appDatabase.getExerciseDao().insertAll(exercise);



        //source: https://stackoverflow.com/questions/3053761/reload-activity-in-android
        //source: https://medium.com/@guendouz/room-livedata-and-recyclerview-d8e96fb31dfe
        finish();
        startActivity(getIntent());
        mAdapter.notifyDataSetChanged();
    }
}
