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

public class FlashService extends Service {
    private CameraManager mCameraManager;
    String mCameraId;
    private NotificationManagerCompat notificationManager;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(getBaseContext(), FragmentSoundService.class);
        // PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, notificationIntent, 0);
////
        notificationManager = NotificationManagerCompat.from(getBaseContext());


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
        /// Dodano 30.8.2019
        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentntent = PendingIntent.getActivity(this, 0, activityIntent, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        //////
        Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_2_ID)
                .setContentTitle("SOS Flash Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.sosbeacon1)
                //   .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo_sos))
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

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switchFlashligtOff();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashligtOn() {

        try {
            mCameraManager.setTorchMode(mCameraId, true);

            //blinkFlash();

        } catch (CameraAccessException e) {
            Log.e("Nema Flash", "Nema fleš");
            e.printStackTrace();
        } catch (Exception e) {

            Log.e("Nema Flash Exception", "Nema fleš Exception");
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashligtOff() {

        try {
            mCameraManager.setTorchMode(mCameraId, false);
        } catch (CameraAccessException e) {
            Log.e("Nema Flash", "Nema fleš");
            e.printStackTrace();
        } catch (Exception e) {

            Log.e("Nema Flašh Exception", "Nema fleš Exception");
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


//    private void blinkFlash()   throws CameraAccessException {
//        CameraManager cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
//        String blinkString  = "1111110000000001111111111111111110000000001111111111";
//        long blinkDelay = 100; // Delay u milisekundama
//        for (int i = 0 ; i < blinkString.length(); i++)
//        {
//            if (blinkString.charAt(i)=='1')
//            {
//
//                try {
//
//
//                    String cameraId = cameraManager.getCameraIdList()[0];
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        cameraManager.setTorchMode(cameraId,true);
//                    }
//
//                }
//                catch (Exception e){
//
//
//                    Toast.makeText(getApplicationContext(), e.getMessage().toString()  , Toast.LENGTH_LONG).show();
//                }
//            }
//            else
//            {
//                try{
//                    String cameraId = cameraManager.getCameraIdList()[0];
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        cameraManager.setTorchMode(cameraId,false);
//                    }
//                }
//                catch (Exception e )
//                {
//                    Toast.makeText(getApplicationContext(), e.getMessage().toString()  , Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//
//            try {
//                Thread.sleep(blinkDelay);//ODO vidjeti žašto je ovo jedino pominjanje threada
    //urađeno, ovako se poziva delaj a uopšte ne mora biti primjenjen ni runnable ni nasleđen Thread

//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//          //  flashLightOff();
//
//
//        }
//        catch (Exception e)
//        {
//
//
//        }
    // }



}
