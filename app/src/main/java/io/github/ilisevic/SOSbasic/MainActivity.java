package io.github.ilisevic.SOSbasic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import io.github.ilisevic.SOSbasic.fragment.FragmentSoundService;
import io.github.ilisevic.SOSbasic.fragment.FragmentFlash;
import io.github.ilisevic.SOSbasic.fragment.FragmentFlashService;
import io.github.ilisevic.SOSbasic.fragment.FragmentGPS;
import io.github.ilisevic.SOSbasic.fragment.FragmentWhistle;
import io.github.ilisevic.SOSbasic.utils.Compass;
import io.github.ilisevic.SOSbasic.utils.SOTWFormatter;
import io.github.ilisevic.SOSbasic.utils.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "CompassActivity";

    private Compass compass;
    private ImageView arrowView;
    private TextView sotwLabel;  // SOTW is for "side of the world"
    private FloatingActionButton fab;
    private float currentAzimuth;
    private SOTWFormatter sotwFormatter;

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        tabLayout = findViewById(R.id.tablayout);
        appBarLayout = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.viewpager);
        //
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Manual,Manual, Manual TO DO..", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), ManualActivity.class);
                startActivity(i);


            }
        });

        boolean isFlashAvailable = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean hasSensorCompass = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);

        if (!isFlashAvailable) {
            Toast.makeText(this, "Device got a flash: " + isFlashAvailable, Toast.LENGTH_SHORT).show();

        }


        if (!isFlashAvailable) {


            AlertDialog.Builder alert;

            alert = new AlertDialog.Builder(this).setTitle("No FlashLight present")
                    .setMessage("No FlashLight present, some APP function might not work")
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

        //ALERT za Compass


        if (!hasSensorCompass) {

            AlertDialog.Builder alert;

            alert = new AlertDialog.Builder(this).setTitle("No compass sensor present")
                    .setMessage("No compass sensor present, compass will not work")
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
        adapter.addFragment(new FragmentGPS(), getString(R.string.labelGPSlocation));
        adapter.addFragment(new FragmentFlash(), "FLASH LAMP");
        adapter.addFragment(new FragmentFlashService(), "FLASH S.O.S");
        adapter.addFragment(new FragmentWhistle(), "Whistler");
        adapter.addFragment(new FragmentSoundService(), "AUDIO S.O.S");
        // adapter.addFragment(new FragmentHelpManual(), "MANUAL");
        //podešavanje adaptera
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        sotwFormatter = new SOTWFormatter(this);

        arrowView = findViewById(R.id.main_image_hands);
        sotwLabel = findViewById(R.id.sotw_label);


        setupCompass();

        if (!hasSensorCompass) sotwLabel.setText("No compass sensor available");


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
