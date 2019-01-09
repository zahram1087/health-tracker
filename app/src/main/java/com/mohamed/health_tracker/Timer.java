package com.mohamed.health_tracker;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Timer extends AppCompatActivity {

    /** FOR CLOCK FUNCTIONALITY */

    /** reference the following code for my timer solution: source: https://stackoverflow.com/questions/4597690/android-timer-how-to CLOCK FUNCTIONALITY */

    TextView timerView;
    long initialTime = 0; //startTime
    long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

                //FOR TIMER
        timerView = (TextView) findViewById(R.id.editText2);
        final Button startPauseButton = findViewById(R.id.toggleButton);

        startPauseButton.setText("Start");


        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startPauseButton.getText().equals("Pause")) {
                    currentTime = System.currentTimeMillis() - initialTime + currentTime;
                    stopWatchHandler.removeCallbacks(stopWatchRunner);
                    startPauseButton.setText("Start");

                } else {
                    initialTime = System.currentTimeMillis();
                    startPauseButton.setText("Pause");
                    stopWatchHandler.postDelayed(stopWatchRunner, 0);
                }

            }
        });
    }

    Handler stopWatchHandler = new Handler();
    Runnable stopWatchRunner = new Runnable() {
        @Override
        public void run() {
            long millis = (System.currentTimeMillis() - initialTime + currentTime);
            int seconds = (int)(millis/1000);
            int minutes = seconds/60;
            int hours = minutes/60;

            millis = millis%1000;
            seconds = seconds%60;
            minutes = minutes%60;
            hours = hours%24;

            timerView.setText(String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis));
            stopWatchHandler.postDelayed(this, 50);

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        stopWatchHandler.removeCallbacks(stopWatchRunner);
        Button startStopButton = findViewById(R.id.toggleButton);
        startStopButton.setText("Start");
    }

    public void onReset (View v) {
        currentTime = 0;
        stopWatchHandler.removeCallbacks(stopWatchRunner);
        TextView time = findViewById(R.id.editText2);
        time.setText("00:00:00:000");
        Button startStop = findViewById(R.id.toggleButton);
        startStop.setText("Start");
    }

}
