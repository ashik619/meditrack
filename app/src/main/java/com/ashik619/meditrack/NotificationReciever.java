package com.ashik619.meditrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ashik619 on 05-06-2017.
 */
public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAg", "" +"recieved");
        Intent intent1 = new Intent(context, NotIntentService.class);
        intent1.putExtra("name",intent.getStringExtra("name"));
        intent1.putExtra("quant",intent.getStringExtra("quant"));
        context.startService(intent1);
    }
}
