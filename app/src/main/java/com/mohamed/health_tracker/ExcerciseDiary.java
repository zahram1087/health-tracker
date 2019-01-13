package com.mohamed.health_tracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class ExcerciseDiary extends AppCompatActivity {
//    EditText editTitle;
//    EditText editQuantity;
//    EditText editDescription;
//    EditText editTimeStamp;
//    Button submitButton = (Button) findViewById(R.id.save);

    TextView display = (TextView) findViewById(R.id.textView6);
//
//    public static AppDatabase db;
//
//    public static ExerciseDao dao;

//    Exercise excercise;


    //Getting the input: Dealing with the action source: https://www.101apps.co.za/articles/capturing-user-input-with-android-s-textfields.html


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //finding the EditTextby ID:
//        editTitle.findViewById(R.id.editText);
//        editQuantity.findViewById(R.id.editText2);
//        editDescription.findViewById(R.id.edit);
//        editTimeStamp.findViewById(R.id.timeStamp);
//        submitButton.findViewById(R.id.save);


        setContentView(R.layout.activity_excercise_diary);



//        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "excercise").allowMainThreadQueries().build();


        //If you wanted to get all of the EXcercise data that’s in the database:
//        List<Exercise> everyone = db.getExerciseDao().getAllexcercise();


        //Dealing with the action for Title
        EditText editTitle = (EditText) findViewById(R.id.editText);
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
        EditText editQuantity = (EditText) findViewById(R.id.editText2);
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
        EditText editDescription = (EditText) findViewById(R.id.edit);
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
        EditText editTimeStamp = (EditText) findViewById(R.id.timeStamp);
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


//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // fetch data and create Excercise object
//                Exercise excercise;
//
//                String a = editTitle.getText().toString();
//                int b = Integer.parseInt(editQuantity.getText().toString());
//                String c = editDescription.getText().toString();
//                String d = editTimeStamp.getText().toString();
//
//                excercise = new Exercise(a, b, c, d);
//
//                // create worker thread to insert data into database
//                ExcerciseDiary.db.getExerciseDao().insertAll(excercise);
//
//                editTitle.setText("");
//                editDescription.setText("");
//                editQuantity.setText("");
//                editTimeStamp.setText("");
//
//                Toast.makeText(ExcerciseDiary.this, "excercise added succeesfullly", Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }

}


