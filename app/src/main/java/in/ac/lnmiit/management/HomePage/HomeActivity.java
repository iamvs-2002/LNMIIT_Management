package in.ac.lnmiit.management.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import in.ac.lnmiit.management.Modules.BusService;
import in.ac.lnmiit.management.Modules.DispensaryManagement;
import in.ac.lnmiit.management.Modules.HostelLeaveManagement;
import in.ac.lnmiit.management.Modules.StaffLeaveManagement;
import in.ac.lnmiit.management.Modules.TimeTableGeneration;
import in.ac.lnmiit.management.R;

/*
Display a list of all the modules
in a recycler view, containing the name
and background color for each
*/

public class HomeActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.home_drawerLayout);
        navigationView = findViewById(R.id.home_navBar);
        webView = findViewById(R.id.home_webView);

        // Open LNMIIT website in WebView
        String url = "https://www.lnmiit.ac.in";
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);

        // Set the app name on the toolbar
        // Implement Navigation Drawer
        toolbarSetup();
    }
    private void toolbarSetup() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_timetable:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        sendUserToActivity(TimeTableGeneration.class);
                        break;
                    case R.id.menu_busservice:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        sendUserToActivity(BusService.class);
                        break;
                    case R.id.menu_dispensary:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        sendUserToActivity(DispensaryManagement.class);
                        break;
                    case R.id.menu_facultyleave:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        sendUserToActivity(StaffLeaveManagement.class);
                        break;
                    case R.id.menu_hostelleave:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        sendUserToActivity(HostelLeaveManagement.class);
                        break;
                    case R.id.menu_shareapp:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        Toast.makeText(getApplicationContext(), "Share Application", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_aboutus:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        Toast.makeText(getApplicationContext(), "About Us", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    public void sendUserToActivity(Class<? extends Activity> activityClass){
        Intent intent = new Intent(HomeActivity.this, activityClass);
        startActivity(intent);
    }

    private long pressedTime;
    @Override
    public void onBackPressed() {
        // Close the drawer layout, if it is open
        if(drawerLayout.isOpen()){
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        // Press twice to close
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}