package com.boilerplatecode.SOSbasic.utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class App extends Application {


    public static final String CHANNEL_1_ID = "S*O*S Channel Sound Alert";
    public static final String CHANNEL_2_ID = "S*O*S Channel Flash Alert";

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
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_1_ID, "SOS Brodcast Channel", NotificationManager.IMPORTANCE_HIGH);
            //TODO obavezno probati  serviceChannel.setLightColor();
            serviceChannel.setDescription("Opis Deskripcije");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

            ////// dodano onako
            //može biti uzrok negašenja 2 notifikacione linije tj za flash
            NotificationChannel serviceChannel2 = new NotificationChannel(CHANNEL_2_ID, "SOS Brodcast Channel 2", NotificationManager.IMPORTANCE_LOW);
            //TODO obavezno probati  serviceChannel.setLightColor();
            serviceChannel.setDescription("Opis Deskripcije 2");
            NotificationManager manager2 = getSystemService(NotificationManager.class);
            manager2.createNotificationChannel(serviceChannel2);


            //////

        }


    }
}
