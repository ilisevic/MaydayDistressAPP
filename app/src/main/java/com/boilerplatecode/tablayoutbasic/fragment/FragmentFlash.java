package com.boilerplatecode.tablayoutbasic.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.boilerplatecode.tablayoutbasic.R;

public class FragmentFlash extends Fragment {
View view;
ToggleButton toggleButton;
CameraManager mCameraManager;
      String mCameraId;
    public FragmentFlash()
    {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.b_fragment, container,false);


        boolean isFlashAvailable = getActivity().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        Toast.makeText(getContext(), "Device got a flash: " + isFlashAvailable, Toast.LENGTH_LONG).show();


        if (!isFlashAvailable) {
            showNoFlashError();


        }

        mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);


        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.e("Nema Flash", "Nema fleš");
        }

        toggleButton = view.findViewById(R.id.btn_toggle);

        toggleButton.setText("TorchLight ON/OFF");

        toggleButton.setTextOn("TorchLight ON");
        toggleButton.setTextOff("TorchLight OFF");
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                switchFlashligtOn(isChecked);

            }
        });





        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashligtOn(boolean isChecked) {

        try {
            mCameraManager.setTorchMode(mCameraId, isChecked);
        } catch (CameraAccessException e) {
            Log.e("Nema Flash", "Nema fleš");
            e.printStackTrace();
        } catch (Exception e) {

            Log.e("Nema Flash Exception", "Nema fleš Exception");
            e.printStackTrace();
        }
    }

    private void showNoFlashError() {

        AlertDialog.Builder alert;

        alert = new AlertDialog.Builder(getContext()).setTitle("Nije podržan flash")
                .setMessage("Nije podržan camera flash")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
