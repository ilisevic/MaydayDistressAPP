package com.boilerplatecode.SOSbasic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.boilerplatecode.SOSbasic.MainActivity;
import com.boilerplatecode.SOSbasic.fragment.FragmentSoundService;
import com.boilerplatecode.SOSbasic.R;

import static com.boilerplatecode.SOSbasic.utils.App.CHANNEL_1_ID;

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
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.sosmorsecode1);
        Intent notificationIntent = new Intent(getBaseContext(), FragmentSoundService.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_1_ID)
                .setContentTitle("SoS Sound Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.audio)
                // .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo_sos))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
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
