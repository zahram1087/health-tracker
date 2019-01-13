package com.mohamed.health_tracker;

//db dependencies: source: https://developer.android.com/topic/libraries/architecture/adding-components
//used the pre-androidX room version

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Exercise {

    //source: https://developer.android.com/training/data-storage/room/defining-data

    @PrimaryKey
    public int id;
    public String title;
    public int quantity;
    public String description;
    public String timestamp;

    public Exercise () {}

    //constructor
    public Exercise (String title, int quantity, String description, String timestamp){

        this.title=title;
        this.quantity=quantity;
        this.description=description;
        this.timestamp=timestamp;

    }

}
