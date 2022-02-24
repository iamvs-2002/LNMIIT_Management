package in.ac.lnmiit.management.Modules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import in.ac.lnmiit.management.R;

public class DispensaryManagement extends AppCompatActivity {

    private Toolbar toolbar;
    LinearLayout studentLayout, doctorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary_management);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dispensary Management");
        setSupportActionBar(toolbar);

        studentLayout = findViewById(R.id.student_dispensary_layout);
        doctorLayout = findViewById(R.id.doctor_dispensary_layout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dispensarymenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.dispensary_doctor_login_menuoption:
                doctorLayout.setVisibility(View.VISIBLE);
                studentLayout.setVisibility(View.GONE);
                return true;
            case R.id.dispensary_student_login_menuoption:
                doctorLayout.setVisibility(View.GONE);
                studentLayout.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}