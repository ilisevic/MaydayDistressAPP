package io.github.ilisevic.SOSbasic;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.FontRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#DF2520"))
                // .withHeaderText("To be sure that compass shows right direction please calibrate the phone's sensor magnetometer by holding phone up and moving it in a figure of number 8.")
                // .withFooterText("\"To be sure that compass shows right direction please calibrate the phone's sensor magnetometer by holding phone up and moving it in a figure of number 8.\"")
                // .withBeforeLogoText("To be sure that compass shows right direction please calibrate the phone's sensor magnetometer by holding phone up and moving it in a figure of number 8.")
                // .withAfterLogoText("\"To be sure that compass shows right direction please calibrate the phone's sensor magnetometer by holding phone up and moving it in a figure of number 8.\"")
                .withBackgroundResource(R.drawable.splashlogo);
        // .withLogo(R.mipmap.ic_splash_screen_foreground);//TODO promjeniti  drugim logom

        // Typeface atwriter = Typeface.setTypeface(ResourcesCompat.getFont(context, R.font.abc_font))
        //     createFromAsset(getAssets(), ResourcesCompat.getFont(this.getApplicationContext(),R.font.atwriter));
        //  ResourcesCompat.getFont(context, R.font.atwriter);

//        config.getHeaderTextView().setTextColor(Color.WHITE);
//        config.getHeaderTextView().setTextSize(2);
//        config.getFooterTextView().setTextColor(Color.WHITE);
//        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
//        config.getBeforeLogoTextView().setTextSize(2);

        //config.getAfterLogoTextView().setTextColor(Color.WHITE);
        //    config.getAfterLogoTextView().setTypeface(atwriter);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}