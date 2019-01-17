package com.mohamed.health_tracker;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

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

    //STARTING THE API REQUEST
    //source:https://developer.android.com/training/volley/simple
    //source: https://stackoverflow.com/questions/8371274/how-to-parse-json-array-with-gson/8371455

    // Instantiate the RequestQueue.

    public  void requestingBody() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://exercise-health-app.herokuapp.com/exercise";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Gson gson = new Gson();
                        String jsonOutput = "title: "; //list 
                        Type listType = new TypeToken<List<Exercise>>(){}.getType();
                        List<Exercise> posts = gson.fromJson(jsonOutput, listType);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
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
        //source: https://developer.android.com/guide/components/fundamentals
        finish();
        startActivity(getIntent());
        mAdapter.notifyDataSetChanged();
    }
}
