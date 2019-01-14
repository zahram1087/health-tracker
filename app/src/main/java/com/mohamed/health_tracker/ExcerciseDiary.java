package com.mohamed.health_tracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Date;
import java.util.List;

public class ExcerciseDiary extends AppCompatActivity {

    //display the data in the dataBase
    private static AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //input names
    EditText editTitle;
    EditText editQuantity;
    EditText editDescription;
    EditText editTimeStamp;
    Button submitButton = (Button) findViewById(R.id.save);


    //Getting the input: Dealing with the action source: https://www.101apps.co.za/articles/capturing-user-input-with-android-s-textfields.html


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_diary);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exercise").allowMainThreadQueries().build();

        //Help from Jessica Lovell:
        recyclerView = (RecyclerView) findViewById(R.id.diaryRecycler);
        recyclerView.setHasFixedSize(true);

        // define an adapter
        mAdapter = new MyAdapter(appDatabase.getExerciseDao().getAll());
        recyclerView.setAdapter(mAdapter);


//        //finding the EditTextby ID:
//        editTitle.findViewById(R.id.editText);
//        editQuantity.findViewById(R.id.editText2);
//        editDescription.findViewById(R.id.edit);
//        editTimeStamp.findViewById(R.id.timeStamp);
//        submitButton.findViewById(R.id.save);


        //Dealing with the action for Title
        editTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //show toast for input
                    String inputText = v.getText().toString();
                    Toast.makeText(ExcerciseDiary.this, "Title is: " + inputText, Toast.LENGTH_SHORT).show();
                }

                return handled;
            }
        });


        //Dealing with the action for Quantity
        editQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //show toast for input
                    String inputText = v.getText().toString();
                    Toast.makeText(ExcerciseDiary.this, "Quantity is: " + inputText, Toast.LENGTH_SHORT).show();
                }

                return handled;
            }
        });

        //Dealing with the action for Description
        editDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //show toast for input
                    String inputText = v.getText().toString();
                    Toast.makeText(ExcerciseDiary.this, "Description is: " + inputText, Toast.LENGTH_SHORT).show();
                }

                return handled;
            }
        });

        //Dealing with the action for timeStamp
        editTimeStamp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //show toast for input
                    String inputText = v.getText().toString();
                    Toast.makeText(ExcerciseDiary.this, "Time is: " + inputText, Toast.LENGTH_SHORT).show();

                    //The following codes are to: Closing keyboard/code to close the keyboard
                    //Use the InputMethodManager to get an instance of the IME service
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //Use hideSoftInputFromWindow() to hide the keyboard
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //return handled - it returns true as the input action is finished. No more input will be expected
                    handled = true;
                }

                return handled;
            }
        });

    }

    //saving the diary entry to the dataBase

    public void addDiaryEntryOnButtonClick(View view){

        //finding the EditTextby ID:
        editTitle.findViewById(R.id.editText);
        editQuantity.findViewById(R.id.editText2);
        editDescription.findViewById(R.id.edit);
        String timestamp = new Date().toString();
        submitButton.findViewById(R.id.save);

        // fetch data and create Excercise object
        Exercise exercise = new Exercise(editTitle.getText().toString(), editQuantity.getText().toString(), editDescription.getText().toString(), timestamp);
        appDatabase.getExerciseDao().insertAll(exercise);

        //source: https://stackoverflow.com/questions/3053761/reload-activity-in-android
        finish();
        startActivity(getIntent());



    }

}


