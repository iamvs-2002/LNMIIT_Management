package in.ac.lnmiit.management.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import in.ac.lnmiit.management.R;

/*
Display a list of all the modules
in a recycler view, containing the name
and background image for each
 */


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}