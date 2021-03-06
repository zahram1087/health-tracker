package com.mohamed.health_tracker;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FingerExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_exercise);
        displayUserName();
    }

    /** FOR INCREASE FUNCTIONALITY */
    /** Called when the user taps the Send button */
    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.editText);


        if(number % 50 == 0 && number != 0){
            displayInteger.setText("YOUR SUPER STRONG");
        }

        else if(number % 10 == 0){
            displayInteger.setText("WOW SUPER FINGER!");
        }else{
            displayInteger.setText("Clicks: " + number);
        }
    }

    int startNumber = 0;

    //call the display method and passes the redefined startNumber
    public void increaseInteger(View view) {
        startNumber = startNumber + 1;
        display(startNumber);
    }

    //HOME ROUTES
    public void goToOtherApps(View view){

        Intent goHomeIntent = new Intent(this, MainActivity.class);
        startActivity(goHomeIntent);

    }

    public void displayUserName(){
        //source:https://developer.android.com/training/data-storage/shared-preferences
        //Evan helped me also

        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.username), Context.MODE_PRIVATE);
        String username = sharedPref.getString(getString(R.string.username), "enter user name");
        TextView userData = findViewById(R.id.username3);
        userData.setText("welcome, " + username);

    }

}
