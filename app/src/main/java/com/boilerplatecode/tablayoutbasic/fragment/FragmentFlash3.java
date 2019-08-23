package com.boilerplatecode.tablayoutbasic.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.boilerplatecode.tablayoutbasic.R;


public class FragmentFlash3 extends Fragment {
    private Button btnEnable;
    private ImageView imageFlashLight;
    private static final int CAMERA_REQUEST = 50;
    private boolean flashLightStatus = false;
    private ToggleButton toggleButton;

    public FragmentFlash3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_flash2, container, false);


        imageFlashLight = view.findViewById(R.id.imageFlashLight);
        btnEnable = view.findViewById(R.id.buttonEnable);
        toggleButton = view.findViewById(R.id.togglebtn);
        final boolean hasCameraFlash = getActivity().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        Toast.makeText(getContext(), "hasCameraFlash " + hasCameraFlash, Toast.LENGTH_SHORT).show();


        boolean isEnabled = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
//0ODO ponovo napisati isEnabled dio -
        // boolean isEnabled =  ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        if (isEnabled) btnEnable.setVisibility(View.GONE);
        Toast.makeText(getContext(), "isEnabled " + isEnabled, Toast.LENGTH_SHORT).show();


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    try {
                        blinkFlash();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Log je: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {

                    try {


                        flashLightOff();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Log je: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        btnEnable.setEnabled(!isEnabled);
        imageFlashLight.setEnabled(isEnabled);
        btnEnable.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                                         }


                                     }


        );


        imageFlashLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (hasCameraFlash) {

                    if (flashLightStatus) {
                        flashLightOff();
                    } else {

                        try {
                            blinkFlash();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }

                    }


                } else {
                    Toast.makeText(getActivity(), "No flash on your device", Toast.LENGTH_SHORT).show();


                }
            }
        });


        return view;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];

            cameraManager.setTorchMode(cameraId, true);
            flashLightStatus = true;
            imageFlashLight.setImageResource(R.drawable.btn_switch_on);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void flashLightOff() {

        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];

            cameraManager.setTorchMode(cameraId, false);
            flashLightStatus = false;
            imageFlashLight.setImageResource(R.drawable.btn_switch_off);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    btnEnable.setEnabled(false);
                    btnEnable.setText("Camera Enabled");
                    btnEnable.setVisibility(View.GONE);
                    imageFlashLight.setEnabled(true);
                } else {

                    Toast.makeText(getActivity(), "Permission Denied for Camera", Toast.LENGTH_SHORT).show();


                }

                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void blinkFlash() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        String blinkString = "1111110000000001111111111111111110000000001111111111";
        long blinkDelay = 100; //seekBar.getProgress(); // Delay u milisekundama
        for (int i = 0; i < blinkString.length(); i++) {
            if (blinkString.charAt(i) == '1') {

                try {


                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, true);

                } catch (Exception e) {


                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                try {
                    String cameraId = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraId, false);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }

            try {
                Thread.sleep(blinkDelay);//TODO vidjeti žašto je ovo jedino pominjanje threada
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            flashLightOff();

            // toggleButton.setChecked(false);
        } catch (Exception e) {


        }
    }

}