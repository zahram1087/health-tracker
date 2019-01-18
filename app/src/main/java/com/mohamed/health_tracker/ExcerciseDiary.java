package com.mohamed.health_tracker;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcerciseDiary extends AppCompatActivity {


    //display the data in the dataBase
    private Exercise exercise;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //created the listDataBase for adding the API db stuff
    private List<Exercise> serverDatabase;

    //getting location
    private FusedLocationProviderClient mFusedLocationClient;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_diary);

        //for location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //calling the server database
        getServerDataBase();

        //checking location
        getLocation();


    }


    //saving the diary entry to the dataBase
    public void addDiaryEntryOnButtonClick(View view) {

        //finding the EditTextby ID:
        EditText editTitle = findViewById(R.id.title);
        EditText editQuantity = findViewById(R.id.quantity);
        EditText editDescription = findViewById(R.id.description);

        Date now = new Date();
        String timestamp = DateFormat.format("M/d/yy h:mma", now).toString();

//        String timestamp = new Date().toString();


        // fetch data and create Excercise object
        Exercise exercise = new Exercise(editTitle.getText().toString(), editQuantity.getText().toString(), editDescription.getText().toString(), timestamp);
        appDatabase.getExerciseDao().insertAll(exercise);

        //calling the saveToServerDatabase method here: pass in the same above values
        saveToServerDatabase(editTitle.getText().toString(), editQuantity.getText().toString(), editDescription.getText().toString());

        //source: https://stackoverflow.com/questions/3053761/reload-activity-in-android
        //source: https://medium.com/@guendouz/room-livedata-and-recyclerview-d8e96fb31dfe
        //source: https://developer.android.com/guide/components/fundamentals

        finish();
        startActivity(getIntent());
//        mAdapter.notifyDataSetChanged();

    }


    //STARTING THE API REQUEST
    //source:https://developer.android.com/training/volley/simple
    //source: https://stackoverflow.com/questions/8371274/how-to-parse-json-array-with-gson/8371455

    // Instantiate the RequestQueue.
    public void getServerDataBase() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://exercise-health-app.herokuapp.com/exercise";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //source:https://stackoverflow.com/questions/8371274/how-to-parse-json-array-with-gson/8371455
                        Gson gson = new Gson();
                        //b/c json doesn't know to turn into a list
                        Type listType = new TypeToken<List<Exercise>>() {
                        }.getType();
                        List<Exercise> serverResponse = gson.fromJson(response, listType);
                        serverDatabase = serverResponse;

                        //combining the two databases, to essentially display both at once
                        displayDataFromRecycleView();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Diary.getServer", error.toString());

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    //displayDataFromRecycleView

    public void displayDataFromRecycleView() {
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").allowMainThreadQueries().build();

        if (appDatabase.getExerciseDao().getAllexcercise().isEmpty()) {
            Date now = new Date();
            String timestamp = DateFormat.format("M/d/yy h:mma", now).toString();
            appDatabase.getExerciseDao().insertAll(new Exercise("running", "5", "running in place", timestamp));
        }
//        //checking database working
//        Exercise testing = new Exercise("testing", "testing","testing", "testing");
//        appDatabase.getExerciseDao().insertAll(testing);


        //combing external database and local database(adding what is appDB to serverDB)
        serverDatabase.addAll(appDatabase.getExerciseDao().getAllexcercise());


        //source: http://www.vogella.com/tutorials/AndroidRecyclerView/article.html
        recyclerView = (RecyclerView) findViewById(R.id.diaryRecycler);
        recyclerView.setHasFixedSize(true);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // define an adapter
        mAdapter = new MyAdapter(serverDatabase);
//        mAdapter = new MyAdapter(appDatabase.getExerciseDao().getAllexcercise());
        recyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();

    }


    //Now need to save to Server Database
    public void saveToServerDatabase(final String title, final String quantity, final String description) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://exercise-health-app.herokuapp.com/exercise";

        //The following is going to request a string response from the url.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Diary.getServer", "added to server db");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Diary.getServer", error.toString());

            }
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("quantity", quantity);
                params.put("description", description);

                return params;

            }

        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    //ADDING LOCATION
    public void getLocation() {
        //source:https://developer.android.com/training/permissions/requesting

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                // Permission is not granted
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }

        } else {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            Log.e("Location", "PERMISSION");

                            // Got last known location. In some rare situations this can be null.


                            if (location != null) {
                                // Logic to handle location object
                            }
                        }

                    });
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    Log.e("Location", "PERMISSION");

                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }




    }







