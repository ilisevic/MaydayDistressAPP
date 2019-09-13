package com.boilerplatecode.SOSbasic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

//import static com.boilerplatecode.SOSbasic.AppFlash.CHANNEL_FLASH_ID;
import com.boilerplatecode.SOSbasic.MainActivity;
import com.boilerplatecode.SOSbasic.fragment.FragmentSoundService;
import com.boilerplatecode.SOSbasic.R;

import static com.boilerplatecode.SOSbasic.utils.App.CHANNEL_2_ID;

public class FlashServiceThread2 extends Service {
    private CameraManager mCameraManager;
    private String mCameraId;
    private NotificationManagerCompat notificationManager;
    public volatile boolean flashRunning = true;
    volatile private Thread thread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(getBaseContext(), FragmentSoundService.class);

        notificationManager = NotificationManagerCompat.from(getBaseContext());




        mCameraManager = (CameraManager) getBaseContext().getSystemService(Context.CAMERA_SERVICE);


        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.e("Nema Flash", "Nema fleš");
        }


        RunnableFlash runnableFlash = new RunnableFlash();


        thread = new Thread(runnableFlash);
        thread.start();


        //////
        /// Dodano 30.8.2019
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentntent = PendingIntent.getActivity(this, 0, activityIntent, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        //////
        Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_2_ID)
                .setContentTitle("SOS Flash Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.sosbeacon1)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                //dodano 30.8.2019
                .setContentIntent(pendingIntent)
                //
                .build();
        //promjenjeno 30.8.2019 TODO provjeriti kada startForegroun a kada NotificationCompatManager()
        // notificationManager.notify(2, notification);
        startForeground(2, notification);

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
//ovo ubija samo prvi thread, ostali prolaze mimo ovoga
        flashRunning = false;
        thread.interrupt();
        super.onDestroy();




    }


    ///TODO odavde nastaviti: napraviti loop u threadu
    private synchronized void blinkFlash() throws CameraAccessException {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String blinkString = "1110001111111000111000000000000";
        long blinkDelay = 100; // Delay u milisekundama


        while (flashRunning) {

            ///
            for (int i = 0; i < blinkString.length(); i++) {
                if (!flashRunning) {

                    thread.interrupt();
                    break;
                }//ako je TRUE dio ispod otpada, ako je FALSE, ide dalje
                try {
                    Thread.sleep(blinkDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (blinkString.charAt(i) == '1') {

                    try {


                        String cameraId = mCameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            // if(!flashRunning) return;
                            mCameraManager.setTorchMode(cameraId, true);
                        }

                    } catch (Exception e) {


                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        String cameraId = mCameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mCameraManager.setTorchMode(cameraId, false);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }


            }


        }

        try {
            String cameraId = mCameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(cameraId, false);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

        }


        //////

    }

    class RunnableFlash implements Runnable {

        @Override
        public void run() {
            //    if (flashRunning) return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //switchFlashligtOn();

                try {
                    blinkFlash();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
