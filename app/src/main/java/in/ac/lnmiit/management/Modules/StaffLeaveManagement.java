package in.ac.lnmiit.management.Modules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import in.ac.lnmiit.management.R;

public class StaffLeaveManagement extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_leave_management);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Staff Leave Management");
        setSupportActionBar(toolbar);
    }
}