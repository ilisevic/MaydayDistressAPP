package com.boilerplatecode.tablayoutbasic;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.boilerplatecode.tablayoutbasic.fragment.FragmentAudioService;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentFlash;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentFlash2;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentFlash3;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentFlashService;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentGPS;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentHelpManual;
import com.boilerplatecode.tablayoutbasic.fragment.FragmentSound;
import com.boilerplatecode.tablayoutbasic.utils.Compass;
import com.boilerplatecode.tablayoutbasic.utils.SOTWFormatter;
import com.boilerplatecode.tablayoutbasic.utils.ViewPagerAdapter;


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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Dodavanje fragmenta
        adapter.addFragment(new FragmentGPS(), "GPS LOCATION");
        adapter.addFragment(new FragmentFlash(), "FLASH");
        adapter.addFragment(new FragmentSound(), "AUDIO WARNING");
        adapter.addFragment(new FragmentFlash2(), "FLASH 2");
        adapter.addFragment(new FragmentFlash3(), "FLASH 3");
        adapter.addFragment(new FragmentAudioService(), "AUDIO SERVICE WARNING");
        adapter.addFragment(new FragmentFlashService(), "FLASH SERVICE");
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
