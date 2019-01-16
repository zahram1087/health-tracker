package com.mohamed.health_tracker;


import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.core.content.FileProvider;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    /**
     * FOR CAMERA FUNCTIONALITY
     */
    //source: https://medium.com/google-developers/detecting-camera-features-with-camera2-61675bb7d1bf

    //Step 1: Get a Camera
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private ImageView imageView;


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    //FOR NOTIFICATION
    java.util.Timer time = new java.util.Timer();

    /**
     * FOR NOTIFICATION FUNCTIONALITY
     */

    private static final String CHANNEL_ID = "channelid";

    private int notificationId = 1;


    /**
     * FOR CAROUSEL FUNCTIONALITY
     */
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

        //DATABASE INSTANCE
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "exercise").build();

    }

    /** FOR CAMERA FUNCTIONALITY */


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }


    //FUNCTIONALITY TO GO TO OTHER ACTIVITIES:

    public void goToFingerExcercise(View view) {

        Intent fingerExcerciseIntent = new Intent(this, FingerExercise.class);
        startActivity(fingerExcerciseIntent);

    }


    public void goToTimer(View view) {

        Intent timerIntent = new Intent(this, com.mohamed.health_tracker.Timer.class);
        startActivity(timerIntent);

    }

    public void goToExcerciseDiary(View view) {

        Intent fingerExcerciseDiaryIntent = new Intent(this, ExcerciseDiary.class);
        startActivity(fingerExcerciseDiaryIntent);

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

    /**
     * reference the following for notification build solution: source: //https://developer.android.com/training/notify-user/build-notification and https://gist.github.com/BrandonSmith/6679223
     */

    //set the notification's content and channel using a NotificationCompat.Builder object
    public void fireNotification() {
        //user timer inspired by: https://stackoverflow.com/questions/4249542/run-a-task-every-x-minutes-with-windows-task-scheduler


//        java.util.Timer time = new java.util.Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.img1)
                        .setContentTitle("Health Notifications")
                        .setContentText("Drink Water")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);

                notificationManager.notify(notificationId++, mBuilder.build());


            }
        }, 5000, 5000);
    }

        /** reference the following for notification channel build method :source: https://developer.android.com/training/notify-user/channels*/

        private void createNotificationChannel () {


            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = CHANNEL_ID;
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }

        public void stopNotifications (View view){

            time.cancel();
            
        }

    }


