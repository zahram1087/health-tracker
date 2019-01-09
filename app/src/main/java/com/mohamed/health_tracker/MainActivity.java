package com.mohamed.health_tracker;

import android.app.ActionBar;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.sql.Time;
import java.text.DateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    //FOR CAROLSE
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};
    String [] quote = {"You are amazing", "Keep giving your best", "Love yourself", "You are super!"};


    /** FOR CLOCK FUNCTIONALITY */

    /** reference the following code for my timer solution: source: https://stackoverflow.com/questions/4597690/android-timer-how-to CLOCK FUNCTIONALITY */

//    TextView timerView;
//    long initialTime = 0; //startTime
//    long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //FOR CAROLE:
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

//        //FOR TIMER
//        timerView = (TextView) findViewById(R.id.editText2);
//        final Button startPauseButton = findViewById(R.id.toggleButton);
//
//        startPauseButton.setText("Start");
//
//
//        startPauseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (startPauseButton.getText().equals("Pause")) {
//                    currentTime = System.currentTimeMillis() - initialTime + currentTime;
//                    stopWatchHandler.removeCallbacks(stopWatchRunner);
//                    startPauseButton.setText("Start");
//
//                } else {
//                    initialTime = System.currentTimeMillis();
//                    startPauseButton.setText("Pause");
//                    stopWatchHandler.postDelayed(stopWatchRunner, 0);
//                }
//
//            }
//        });
    }


    public void goToFingerExcercise(View view){

        Intent fingerExcerciseIntent = new Intent(this, FingerExercise.class);
        startActivity(fingerExcerciseIntent);

    }


    public void goToTimer(View view){

        Intent timerIntent = new Intent(this, Timer.class);
        startActivity(timerIntent);

    }
//    Handler stopWatchHandler = new Handler();
//    Runnable stopWatchRunner = new Runnable() {
//        @Override
//        public void run() {
//            long millis = (System.currentTimeMillis() - initialTime + currentTime);
//            int seconds = (int)(millis/1000);
//            int minutes = seconds/60;
//            int hours = minutes/60;
//
//            millis = millis%1000;
//            seconds = seconds%60;
//            minutes = minutes%60;
//            hours = hours%24;
//
//            timerView.setText(String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis));
//            stopWatchHandler.postDelayed(this, 50);
//
//        }
//    };
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        stopWatchHandler.removeCallbacks(stopWatchRunner);
//        Button startStopButton = findViewById(R.id.toggleButton);
//        startStopButton.setText("Start");
//    }
//
//    public void onReset (View v) {
//        currentTime = 0;
//        stopWatchHandler.removeCallbacks(stopWatchRunner);
//        TextView time = findViewById(R.id.editText2);
//        time.setText("00:00:00:000");
//        Button startStop = findViewById(R.id.toggleButton);
//        startStop.setText("Start");
//    }


/** FOR CAROUSEL FUNCTIONALITY */

    /** Pulledin the following library for my Carousel solution: https://github.com/sayyam/carouselview  */


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);

            TextView inspo = (TextView) findViewById(R.id.textView2);

            inspo.setText(quote[position]);

            }



    };




    /** FOR INCREASE FUNCTIONALITY */
    /** Called when the user taps the Send button */


    /** FOR CLOCK FUNCTIONALITY */

    /** reference the following code for my timer solution: source: https://stackoverflow.com/questions/4597690/android-timer-how-to CLOCK FUNCTIONALITY */


}

