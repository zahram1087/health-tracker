package com.mohamed.health_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;

public class CameraActivity extends AppCompatActivity {

    //source: https://developer.android.com/training/camera/photobasics
    //source: https://developer.android.com/training/permissions/requesting
    //Also code is inspired by Nick, He found the best resources and how to implement them effectively
    private ImageView avatar;
    private boolean CAMERA_PERMISSION;
    private boolean FILES_PERMISSION;
    private static final int REQUEST_ID = 12;
    private String newAvatarUri;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        avatar = findViewById(R.id.avatarProfile);
        preferences = getSharedPreferences("userPrefs", 0);


        //calling method to displaying the saved image if it exist in the filepath
        if (!preferences.contains("avatarUri")) {
            displayAvatar(null);
        } else {
            displayAvatar(preferences.getString("avatarUri", ""));
        }
    }

    //method that displays the image user took or displays default image frm drawable
    private void displayAvatar(String imagePath) {
        if (imagePath == null) {
            avatar.setImageResource(R.drawable.img1);
        } else {
            File image = new File(imagePath);
            Bitmap avatarImage = BitmapFactory.decodeFile(image.getAbsolutePath());
            avatar.setImageBitmap(avatarImage);
        }

    }

    //gets attached to a button that saves the image taken by user
    public void saveChanges(View view){
        SharedPreferences.Editor editor = preferences.edit();
        if (newAvatarUri != null) {
            editor.putString("avatarUri", newAvatarUri);
        }
        editor.apply();
        setResult(RESULT_OK);
        finish();
    }


    //allowing users to pick an image from their phone
    public void selectAvatar(View v) {
        verifyPermissions();
        if (FILES_PERMISSION) {
            //This comes from https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, 2);
        }
    }




    //Called when the user want to upload a new photo. Calls the method to create a new file for that photo, then starts the intent to use the camera
    public void updateAvatar(View v) {
        verifyPermissions();
        if (CAMERA_PERMISSION && FILES_PERMISSION) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.i("FileCreationError", ex.toString());
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        }
    }



    //fires after the user finishes taking a picture or uploading one from their phhone.thi methode is optomatically called to handle that activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Case of the user took a new picture for their avatar
        if (requestCode == 1 && resultCode == RESULT_OK) {
            displayAvatar(newAvatarUri);
        }
        //Case of user went to select a photo from saved images. Grab the proper path for that image, then save it in preferences and call displayAvatar().
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri imagePath = data.getData();
            String realPath = getRealPathFromURI(this, imagePath);
            newAvatarUri = realPath;
            displayAvatar(newAvatarUri);
        }
    }

    //checking for camera permission, request if needed
    public void verifyPermissions() {

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ID);
        } else {
            CAMERA_PERMISSION =true;
            FILES_PERMISSION = true;
        }
    }

    //Code needed to check if the user actually granted permissions after requesting
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CAMERA_PERMISSION = true;
                } else {
                    CAMERA_PERMISSION = false;
                } if (grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    FILES_PERMISSION = true;
                } else {
                    FILES_PERMISSION = false;
                }
            }

        }
    }


    //Used when the user wants to take a new photo for their avatar. Creates a file to save it to and sets the location to that image in preferences
    //source:https://developer.android.com/training/camera/photobasics
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
        newAvatarUri = image.getAbsolutePath();
        return image;
    }


    //This method come from https://stackoverflow.com/questions/3401579/get-filename-and-path-from-uri-from-mediastore
    //It takes the URI that comes back from a selected image and transforms it into a usable path
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
