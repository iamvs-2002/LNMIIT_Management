package in.ac.lnmiit.management.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import in.ac.lnmiit.management.HomePage.HomeActivity;
import in.ac.lnmiit.management.R;

/*
Splash Screen - containing name, logo and an animation
 */

public class SplashActivity extends AppCompatActivity {

    private static int TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Launch HomeActivity after a delay of TIME_OUT milliseconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchHomeScreen();
            }
        }, TIME_OUT);
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}