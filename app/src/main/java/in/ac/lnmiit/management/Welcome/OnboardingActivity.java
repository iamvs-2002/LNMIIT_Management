package in.ac.lnmiit.management.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.ac.lnmiit.management.R;

/*
Onboarding Screen - to be displayed only during first time launch
Onboarding Screen contains 3-4 screens
 */

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
    }
}