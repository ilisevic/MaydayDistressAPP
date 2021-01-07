package io.github.ilisevic.SOSbasic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

import io.github.ilisevic.SOSbasic.MainActivity;
import io.github.ilisevic.SOSbasic.fragment.FragmentSoundService;
import io.github.ilisevic.SOSbasic.R;

import static io.github.ilisevic.SOSbasic.utils.App.CHANNEL_1_ID;

public class SoundService extends Service {
    //TODO uraditi da SoundService radi iz Threada
    private MediaPlayer mediaPlayer;
    volatile private Thread thread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        String input = intent.getStringExtra("inputExtra");
////        mediaPlayer = MediaPlayer.create(this, R.raw.emergency );
//        Intent activityIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);
//        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.sosmorsecode2);
//        Intent notificationIntent = new Intent(getBaseContext(), FragmentSoundService.class);
//        //PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
//        mediaPlayer.setLooping(true);
//        mediaPlayer.start();
//        Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_1_ID)
//                .setContentTitle("SoS Sound Service")
//                .setContentText(input)
//                .setSmallIcon(R.drawable.audio)
//                // .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo_sos))
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setContentIntent(pendingIntent)
//                .build();
//        startForeground(1, notification);


        RunnableSound runnableSound = new RunnableSound();


        thread = new Thread(runnableSound);
        thread.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.interrupt();
        //thread.destroy();
//        mediaPlayer.stop();
//        mediaPlayer.release();
        super.onDestroy();


        mediaPlayer.stop();

    }


    class RunnableSound implements Runnable {

        @Override
        public void run() {
            //    if (flashRunning) return;


            //switchFlashligtOn();

            try {

                String input = "";//intent.getStringExtra("inputExtra");
//        mediaPlayer = MediaPlayer.create(this, R.raw.emergency );
                Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, activityIntent, 0);
                mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.sosmorsecode2);
                Intent notificationIntent = new Intent(getBaseContext(), FragmentSoundService.class);
                //PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_1_ID)
                        .setContentTitle("SoS Sound Service")
                        .setContentText(input)
                        .setSmallIcon(R.drawable.audiow)
                        // .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo_sos))
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(1, notification);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
