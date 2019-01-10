package com.mohamed.health_tracker;

//db dependencies: source: https://developer.android.com/topic/libraries/architecture/adding-components
//used the pre-androidX room version

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Exercise {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "activity_title")
    public String activityTitle;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "timeStamp")
    public long timeStamp;

    @ColumnInfo(name = "activity_description")
    public String activityDescription;

}
