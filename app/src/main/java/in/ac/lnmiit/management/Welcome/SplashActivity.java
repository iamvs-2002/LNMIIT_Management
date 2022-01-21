package in.ac.lnmiit.management.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.ac.lnmiit.management.R;

/*
Splash Screen - containing name, logo and an animation
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}