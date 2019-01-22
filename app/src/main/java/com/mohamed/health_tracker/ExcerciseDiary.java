package com.mohamed.health_tracker;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1890;
    private String exerciseLocation;
    private String errorMessage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_diary);




        //for last known location of phone
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationExercise();

        //calling the server database
        getServerDataBase();

//        //username
//        displayUserName();

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
        Exercise exercise = new Exercise(editTitle.getText().toString(), editQuantity.getText().toString(), editDescription.getText().toString(), timestamp,exerciseLocation);
        appDatabase.getExerciseDao().insertAll(exercise);

        //calling the saveToServerDatabase method here: pass in the same above values
        saveToServerDatabase(editTitle.getText().toString(), editQuantity.getText().toString(), editDescription.getText().toString());

        //source: https://stackoverflow.com/questions/3053761/reload-activity-in-android
        //source: https://medium.com/@guendouz/room-livedata-and-recyclerview-d8e96fb31dfe
        //source: https://developer.android.com/guide/components/fundamentals

        finish();
        startActivity(getIntent());
        //username
        displayUserName();
//        mAdapter.notifyDataSetChanged();
//        getLocationExercise();

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
                        Type listType = new TypeToken<List<Exercise>>() {}.getType();
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
            appDatabase.getExerciseDao().insertAll(new Exercise("running", "5", "running in place", timestamp, "Seattle"));
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
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("quantity", quantity);
                params.put("description", description);
                params.put("location", exerciseLocation);

                return params;

            }

        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    //ADDING LOCATION
    public void getLocationExercise() {
        //source:https://developer.android.com/training/permissions/requesting
        //source:https://developer.android.com/training/location/display-address#java
        //Permission granted:
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.

                            if (location != null) {
                                // Logic to handle location object
                                //source:https://stackoverflow.com/questions/22323974/how-to-get-city-name-by-latitude-longitude-in-android
                                Geocoder geocoder = new Geocoder(ExcerciseDiary.this, Locale.getDefault());

                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                } catch (IOException e) {
                                    // Catch network or other I/O problems.
                                    errorMessage = "service not available";
                                    Log.e("Network", errorMessage, e);
//                                    e.printStackTrace();
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    // Catch invalid latitude or longitude values.
                                    errorMessage = "invalid latitude/longitude used";
                                    Log.e("Location", errorMessage + ". " +
                                            "Latitude = " + location.getLatitude() +
                                            ", Longitude = " +
                                            location.getLongitude(), illegalArgumentException);
                                }

                                exerciseLocation = addresses.get(0).getLocality();

                            }
                              else {

                                    exerciseLocation = "Unavailable";
                                }

                            }

                    });
        }
        else {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //LOG
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    Log.e("Location", "PERMISSION");
                    getLocationExercise();

                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    errorMessage = "Permission denied";
                    Log.e("Location", errorMessage);

                }
                return;
            }

        }
    }

    public void displayUserName(){
        //source:https://developer.android.com/training/data-storage/shared-preferences
        //Evan helped me also

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.username), Context.MODE_PRIVATE);
        String username = sharedPref.getString(getString(R.string.username), "enter user name");
        TextView userData = findViewById(R.id.username2);
        userData.setText("welcome, " + username);

    }
}







