package com.boilerplatecode.SOSbasic;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boilerplatecode.SOSbasic.fragment.FragmentSoundService;
import com.boilerplatecode.SOSbasic.fragment.FragmentFlash;
import com.boilerplatecode.SOSbasic.fragment.FragmentFlashService;
import com.boilerplatecode.SOSbasic.fragment.FragmentGPS;
import com.boilerplatecode.SOSbasic.fragment.FragmentHelpManual;
import com.boilerplatecode.SOSbasic.fragment.FragmentWhistle;
import com.boilerplatecode.SOSbasic.utils.Compass;
import com.boilerplatecode.SOSbasic.utils.SOTWFormatter;
import com.boilerplatecode.SOSbasic.utils.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CompassActivity";

    private Compass compass;
    private ImageView arrowView;
    private TextView sotwLabel;  // SOTW is for "side of the world"

    private float currentAzimuth;
    private SOTWFormatter sotwFormatter;

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout);
        appBarLayout= findViewById(R.id.appbar);
        viewPager=findViewById(R.id.viewpager);
        //


        boolean isFlashAvailable = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        Toast.makeText(this, "Device got a flash: " + isFlashAvailable, Toast.LENGTH_LONG).show();


        if (!isFlashAvailable) {


            AlertDialog.Builder alert;

            alert = new AlertDialog.Builder(this).setTitle("Nije podržan flash")
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


        //
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Dodavanje fragmenta
        adapter.addFragment(new FragmentGPS(), "GPS LOCATION");
        adapter.addFragment(new FragmentFlash(), "FLASH LAMP");
        adapter.addFragment(new FragmentFlashService(), "FLASH S.O.S");
        adapter.addFragment(new FragmentWhistle(), "Whistler");
        adapter.addFragment(new FragmentSoundService(), "AUDIO S.O.S");
        adapter.addFragment(new FragmentHelpManual(), "MANUAL");
        //podešavanje adaptera
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        sotwFormatter = new SOTWFormatter(this);

        arrowView = findViewById(R.id.main_image_hands);
        sotwLabel = findViewById(R.id.sotw_label);
        setupCompass();






    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }

    private void setupCompass() {
        compass = new Compass(this);
        Compass.CompassListener cl = getCompassListener();
        compass.setListener(cl);
    }

    private void adjustArrow(float azimuth) {
        Log.d(TAG, "will set rotation from " + currentAzimuth + " to "
                + azimuth);

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = azimuth;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        arrowView.startAnimation(an);
    }

    private void adjustSotwLabel(float azimuth) {
        sotwLabel.setText(sotwFormatter.format(azimuth));
    }

    private Compass.CompassListener getCompassListener() {
        return new Compass.CompassListener() {
            @Override
            public void onNewAzimuth(final float azimuth) {
                // UI updates only in UI thread
                // https://stackoverflow.com/q/11140285/444966
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adjustArrow(azimuth);
                        adjustSotwLabel(azimuth);
                    }
                });
            }
        };
    }





}
