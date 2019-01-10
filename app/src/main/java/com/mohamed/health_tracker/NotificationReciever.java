package com.mohamed.health_tracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationReciever extends BroadcastReceiver {
    /** FOR NOTIFICATION FUNCTIONALITY */
    /** reference the following for notification build solution: source: //https://developer.android.com/training/notify-user/build-notification and https://gist.github.com/BrandonSmith/6679223*/
    //Purpose of this class: This notification receiver who job is to recieve an intent that contains a notification and then send off that notfication

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // An Intent broadcast.

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification n = intent.getParcelableExtra("notification");
        int id = intent.getIntExtra("notification_id", 0);
        notificationManager.notify(id, n);
    }
}
