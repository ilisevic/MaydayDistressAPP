package com.boilerplatecode.tablayoutbasic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

//import static com.boilerplatecode.tablayoutbasic.AppFlash.CHANNEL_FLASH_ID;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentAudioService;
import com.boilerplatecode.tablayoutbasic.R;

import static com.boilerplatecode.tablayoutbasic.utils.App.CHANNEL_ID;

public class FlashService extends Service {
    private CameraManager mCameraManager;
    String mCameraId;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(getBaseContext(), FragmentAudioService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
////

        boolean isFlashAvailable = getApplicationContext().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        Toast.makeText(getBaseContext(), "Device got a flash: " + isFlashAvailable, Toast.LENGTH_LONG).show();


        if (!isFlashAvailable) {
            //  showNoFlashServiceError();


        }

        mCameraManager = (CameraManager) getBaseContext().getSystemService(Context.CAMERA_SERVICE);


        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.e("Nema Flash", "Nema fleš");
        }

        switchFlashligtOn();
        //////
        Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                .setContentTitle("SOS Flash Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(2, notification);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashligtOn() {

        try {
            mCameraManager.setTorchMode(mCameraId, true);
        } catch (CameraAccessException e) {
            Log.e("Nema Flash", "Nema fleš");
            e.printStackTrace();
        } catch (Exception e) {

            Log.e("Nema Flash Exception", "Nema fleš Exception");
            e.printStackTrace();
        }
    }

    private void showNoFlashServiceError() {

        AlertDialog.Builder alert;

        alert = new AlertDialog.Builder(getBaseContext()).setTitle("Nije podržan flash")
                .setMessage("Nije podržan flash")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO intent koji se pokreće
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert.show();
    }

}
