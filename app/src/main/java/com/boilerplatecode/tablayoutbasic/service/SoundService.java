package com.boilerplatecode.tablayoutbasic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.boilerplatecode.tablayoutbasic.fragment.FragmentAudioService;
import com.boilerplatecode.tablayoutbasic.R;

import static com.boilerplatecode.tablayoutbasic.utils.App.CHANNEL_ID;

public class SoundService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
//        mediaPlayer = MediaPlayer.create(this, R.raw.emergency );
//        Intent notificationIntent =new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.emergency);
        Intent notificationIntent = new Intent(getBaseContext(), FragmentAudioService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                .setContentTitle("SoS Sound Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        mediaPlayer.stop();

    }
}
