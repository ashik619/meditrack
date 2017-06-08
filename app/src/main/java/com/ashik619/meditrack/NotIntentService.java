package com.ashik619.meditrack;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by ashik619 on 05-06-2017.
 */
public class NotIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;


    public NotIntentService() {
        super("NotIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String name = intent.getStringExtra("name");
        String quant = intent.getStringExtra("quant");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Take Medicine");
        builder.setContentText("Name : "+name+" Quantity : "+quant);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Intent notifyIntent = new Intent(this, AddMedicineActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
