package com.mohamed.health_tracker;

//db dependencies: source: https://developer.android.com/topic/libraries/architecture/adding-components
//used the pre-androidX room version


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {

    //source: https://developer.android.com/training/data-storage/room/defining-data

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String title;
    public String quantity;
    public String description;
    public String timestamp;
    public String location;


    public Exercise () {}

    //constructor
    public Exercise (String title, String quantity, String description, String timestamp, String location){

        this.title=title;
        this.quantity=quantity;
        this.description=description;
        this.timestamp=timestamp;
        this.location=location;


    }

    public String toString() {
        return this.title + ": " + this.description + " - " + this.timestamp;
    }

}
