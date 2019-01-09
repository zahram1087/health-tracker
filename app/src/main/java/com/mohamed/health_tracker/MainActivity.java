package com.mohamed.health_tracker;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.sql.Time;
import java.time.Clock;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        // get your ToggleButton
//        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
//
//        // attach an OnClickListener
//        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // The toggle is enabled
//                } else {
//                    // The toggle is disabled
//                }
//            }
//        });
    }

/** FOR INCREASE FUNCTIONALITY */
    /** Called when the user taps the Send button */
    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.editText);
        displayInteger.setText("Clicks: " + number);

        if(number % 10 == 0){
            displayInteger.setText("YOUR SUPER STRONG");
        }

        if(number % 50 == 0){
            displayInteger.setText("WOW SUPER FINGER!");
        }
    }


    int startNumber = 0;

    //call the display method and passes the redefined startNumber
    public void increaseInteger(View view) {
        startNumber = startNumber + 1;
        display(startNumber);

    }
    /** FOR CLOCK FUNCTIONALITY */


    public void nClickToggle(View view) {

        // get your ToggleButton
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);

        // attach an OnClickChangeListener
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    TextView displayTime = (TextView) findViewById(R.id.editText2);

                    displayTime.setText(Long.toString(System.currentTimeMillis()));

                    //formating
                    //continueing the time



                } else {
                    // The toggle is disabled
                    Log.d("BT","BROKE");
                    //STOPS IT AT CURRENT TIME
                    TextView displayTime = (TextView) findViewById(R.id.editText2);

//                    Log.d("time:", Long.toString(System.currentTimeMillis()));
                    displayTime.setText(Long.toString(System.currentTimeMillis()));

                }

            }


        });

    }
}

