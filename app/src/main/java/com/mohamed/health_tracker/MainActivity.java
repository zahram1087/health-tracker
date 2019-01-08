package com.mohamed.health_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    /** Called when the user taps the Send button */
    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.editText);
        displayInteger.setText("" + number);
    }


    int startNumber = 0;

    //call the display method and passes the redefined startNumber
    public void increaseInteger(View view) {
        startNumber = startNumber + 1;
        display(startNumber);

    }

// int num = 0;
    // num = num+1;
    //call this displayMessage
    }

