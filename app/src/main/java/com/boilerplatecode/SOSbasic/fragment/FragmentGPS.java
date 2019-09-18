package com.boilerplatecode.SOSbasic.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import com.boilerplatecode.SOSbasic.R;

import static android.content.Context.LOCATION_SERVICE;


public class FragmentGPS extends Fragment {
    private Button btnGetGps;
    private TextView tv;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String coorLong = "x", coorLat = "y", shareMessage;
    private Button btnSend;

    public FragmentGPS() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gps, container, false);
        btnSend = view.findViewById(R.id.btnSend);
        tv = view.findViewById(R.id.textView);
        btnGetGps = view.findViewById(R.id.btnGetGps);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

//                coorLat = "" + (float) Math.round(location.getLatitude() * 1000000) / 1000000;
//                coorLong = "" + (float) Math.round(location.getLongitude() * 1000000) / 1000000;
                coorLat = "" + (float) location.getLatitude();
                coorLong = "" + (float) location.getLongitude();
                tv.setText("Lat:" + coorLat + "\nLong:" + coorLong);
                shareMessage = "Nalazim se na lokaciji Lat:" + coorLat + ",Long:" + coorLong + " http://google.com/search?q=" + coorLat + "+" + coorLong;

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
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "S.O.S. INFO");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(sharingIntent, "SHARE GPS LOCATION VIA:"));
            }
        });

        return view;
    }
    private void configureButton() {
//ova funkcija provjerava dozvole i pokreće locationManager
        btnGetGps.setOnClickListener(new View.OnClickListener() {
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
                    //TODO  red ispod povezuje locationManager sa (locastionListner)
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
