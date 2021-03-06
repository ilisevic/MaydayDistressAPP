package io.github.ilisevic.SOSbasic;

import android.graphics.Color;

import androidx.annotation.FontRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2500)
                .withBackgroundColor(Color.parseColor("#DF2520"))
                // .withHeaderText("To be sure that compass shows right direction please calibrate the phone's sensor magnetometer by holding phone up and moving it in a figure of number 8.")
                .withFooterText("\n          \b SOS!\n          \b 2021\n Beacon & Locator")
                // .withBeforeLogoText("To be sure that compass shows right direction please calibrate the phone's sensor magnetometer by holding phone up and moving it in a figure of number 8.")
                // .withAfterLogoText("\"Tap to close\"")
                .withBackgroundResource(R.drawable.splash3);
        // .withLogo(R.mipmap.ic_splash_screen_foreground);//TODO promjeniti  drugim logom

        // Typeface atwriter = Typeface.setTypeface(ResourcesCompat.getFont(context, R.font.abc_font))
        //     createFromAsset(getAssets(), ResourcesCompat.getFont(this.getApplicationContext(),R.font.atwriter));
        //  ResourcesCompat.getFont(context, R.font.atwriter);

//        config.getHeaderTextView().setTextColor(Color.WHITE);
//        config.getHeaderTextView().setTextSize(2);
        config.getFooterTextView().setTextColor(Color.LTGRAY);
//        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
//        config.getBeforeLogoTextView().setTextSize(2);
        config.getFooterTextView().setTextSize(12);
        //config.getAfterLogoTextView().setTextColor(Color.WHITE);
        //    config.getAfterLogoTextView().setTypeface(atwriter);

        final View easySplashScreen = config.create();
        setContentView(easySplashScreen);

//     easySplashScreen.setOnClickListener(new View.OnClickListener() {
//         @Override//TODO uraditi gašenje na click
//         public void onClick(View v) {
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            // config.withSplashTimeOut(0);
//             setContentView(easySplashScreen);
//             startActivity(i);
//           //  finish();
//
//         }
//     });
    }
}