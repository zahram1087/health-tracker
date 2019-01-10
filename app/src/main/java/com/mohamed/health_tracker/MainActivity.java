package com.mohamed.health_tracker;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class MainActivity extends AppCompatActivity {
    /** FOR NOTIFICATION FUNCTIONALITY */

    private static final String CHANNEL_ID = "channelid";

    private int notificationId = 1;


    /** FOR CAROUSEL FUNCTIONALITY */
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};
    String[] quote = {"You are amazing", "Keep giving your best", "Love yourself", "You are super!"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /** FOR CAROUSEL FUNCTIONALITY */
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        /** FOR NOTIFICATION FUNCTIONALITY */
        //CREATE THE CHANNEL
        createNotificationChannel();
        //SEND THE NOTIFICATION TO THE CHANNEL
        fireNotification();

    }


    public void goToFingerExcercise(View view) {

        Intent fingerExcerciseIntent = new Intent(this, FingerExercise.class);
        startActivity(fingerExcerciseIntent);

    }


    public void goToTimer(View view) {

        Intent timerIntent = new Intent(this, com.mohamed.health_tracker.Timer.class);
        startActivity(timerIntent);

    }

/** FOR CAROUSEL FUNCTIONALITY */

    /**
     * Pulled in the following library for my Carousel solution: https://github.com/sayyam/carouselview
     */

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);

            TextView inspo = (TextView) findViewById(R.id.textView2);

            inspo.setText(quote[position]);

        }

    };

    /** FOR NOTIFICATION FUNCTIONALITY */

    /** reference the following for notification build solution: source: //https://developer.android.com/training/notify-user/build-notification and https://gist.github.com/BrandonSmith/6679223*/

    //set the notification's content and channel using a NotificationCompat.Builder object


     public void fireNotification () {
         NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                 .setSmallIcon(R.drawable.img1)
                 .setContentTitle("Health Notifications")
                 .setContentText("Drink Water")
                 .setPriority(NotificationCompat.PRIORITY_DEFAULT);

         //pass in the context(this) and class to run to the Intent. The job of this intent is to tell the notification reciever to send a notification
         Intent fireNotificationIntent = new Intent(this, NotificationReciever.class);

         //at this point: include the extra information you will use in the notification (from the NotificationReciever Class)
         fireNotificationIntent.putExtra("notification", mBuilder.build());
         fireNotificationIntent.putExtra("notification_id", notificationId++);

         //pending intent's job is to send out the above intent
         PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, fireNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

         //Next is to schedule the pending intent using an alarm manager
         AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

         alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+5000, pendingIntent);


     }
//https://developer.android.com/training/notify-user/channels
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
//            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

