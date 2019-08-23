package com.boilerplatecode.tablayoutbasic.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.boilerplatecode.tablayoutbasic.R;

import static android.content.Context.LOCATION_SERVICE;


public class FragmentGPS extends Fragment {
    private Button btn;
    private TextView tv;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String coorLong, coorLat;
    private Button btnSend;

    public FragmentGPS() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View  view = inflater.inflate(R.layout.a_fragment,container,false);
        btnSend = view.findViewById(R.id.btnSend);
        tv = view.findViewById(R.id.textView);
        btn = view.findViewById(R.id.btn);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                coorLat =  "Latitude= " +  location.getLatitude();
                coorLong = "Longitude= " + location.getLongitude();

                tv.append("\n " + coorLong + " " + coorLat);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);


            }
        };
        configureButton();
        //return super.onCreateView(inflater, container,savedInstanceState);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO dodati kod za slanje poruke na više app

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subjekt Poruke");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Share Body");
                startActivity(Intent.createChooser(sharingIntent, "SSHHARRE VVIIAA"));
            }
        });

        return view;
    }
    private void configureButton() {
//ova funkcija provjerava dozvole i pokreće locationManager
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
                        }
                        return;
                    }
                    //TODO TODO TODO red ispod povezuje locationManager sa (locastionListner)
                    //*************************

                    locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);//svakin n milisekundi traži lokaciju
                    //**************************

                }
                catch (Exception e){

                    Toast.makeText(getContext(), "Exception :" + e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
