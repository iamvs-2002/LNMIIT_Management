package in.ac.lnmiit.management.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.ac.lnmiit.management.Login.LoginActivity;
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
    private TextView home_personNameTV;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private WebView webView;
    private ProgressBar home_progressBar;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();

        toolbar = findViewById(R.id.toolbar);
        home_personNameTV = findViewById(R.id.home_personNameTV);
        drawerLayout = findViewById(R.id.home_drawerLayout);
        navigationView = findViewById(R.id.home_navBar);
        webView = findViewById(R.id.home_webView);
        home_progressBar = findViewById(R.id.home_progressBar);

        String name = user.getDisplayName();
        // String name = "Vaibhav Singhal";
        home_personNameTV.setText(name);

        // Open LNMIIT website in WebView
        String url = "https://www.lnmiit.ac.in";
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);

        // Set the app name on the toolbar
        // Implement Navigation Drawer
        toolbarSetup();
    }
    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            home_progressBar.setVisibility(View.GONE);
        }
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
                    case R.id.menu_logout:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        signOut();
                        // Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void signOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInClient mGoogleSignInClient ;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getBaseContext(), gso);
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}