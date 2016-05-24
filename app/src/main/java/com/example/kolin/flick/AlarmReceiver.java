package com.example.kolin.flick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by n.kirilov on 24.05.2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, UpdateService.class);
        context.startService(i);
    }
}
