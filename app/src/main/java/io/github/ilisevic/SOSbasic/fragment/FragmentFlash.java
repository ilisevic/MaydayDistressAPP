package io.github.ilisevic.SOSbasic.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import io.github.ilisevic.SOSbasic.R;

public class FragmentFlash extends Fragment {
    View view;
    ToggleButton toggleButton;
    CameraManager mCameraManager;
    String mCameraId;
    ImageView imageView;

    public FragmentFlash() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_flash, container, false);
        imageView = view.findViewById(R.id.imageview_flash);
        imageView.setBackgroundResource(R.drawable.flashw_off);
//
//        boolean isFlashAvailable = getActivity().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
//
//        Toast.makeText(getContext(), "Device got a flash: " + isFlashAvailable, Toast.LENGTH_LONG).show();
//
//
//        if (!isFlashAvailable) {
//            showNoFlashError();
//
//
//        }

        mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);


        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Log.e("No FlashLight present", "No FlashLight present");
        }

        toggleButton = view.findViewById(R.id.btn_toggle);

        toggleButton.setText("START FLASHLIGHT");

        toggleButton.setTextOn("STOP  FLASHLIGHT");
        toggleButton.setTextOff("START FLASHLIGHT");
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                switchFlashligtOn(isChecked);
                if (isChecked) {
                    imageView.setBackgroundResource(R.drawable.flashw);
                } else {
                    imageView.setBackgroundResource(R.drawable.flashw_off);
                }

            }
        });


        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void switchFlashligtOn(boolean isChecked) {

        try {
            mCameraManager.setTorchMode(mCameraId, isChecked);
        } catch (CameraAccessException e) {
            Log.e("No FlashLight present", "No FlashLight present");
            e.printStackTrace();
        } catch (Exception e) {

            Log.e("No FlashLight", "No FlashLight present");
            e.printStackTrace();
        }
    }

    private void showNoFlashError() {

        AlertDialog.Builder alert;

        alert = new AlertDialog.Builder(getContext()).setTitle("No FlashLight present")
                .setMessage("No FlashLight present, some app functions might not work")
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
