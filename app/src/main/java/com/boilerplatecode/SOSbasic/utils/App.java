package com.boilerplatecode.SOSbasic.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class App extends Application {


    public static final String CHANNEL_ID = "exampleServiceChannel";

    //TODO notification channel_id obavezan na Oreu i više
    // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) //TODO naći ispravnu verziju Androida
        {
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Example Service Cannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

        }


    }
}
