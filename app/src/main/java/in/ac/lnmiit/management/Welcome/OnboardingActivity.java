package in.ac.lnmiit.management.Welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.ac.lnmiit.management.R;
import in.ac.lnmiit.management.Welcome.Classes.MyViewPagerAdapter;
import in.ac.lnmiit.management.Welcome.Classes.Preferences;

/*
Onboarding Screen - to be displayed only during first time launch
Onboarding Screen contains 4 screens - 1 for each module
 */

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private static int[] layouts;
    private AppCompatButton btnSkip, btnNext;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        preferences = new Preferences(this);
        if (!preferences.isFirstTimeLaunch()) {
            launchSplashScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        
        setContentView(R.layout.activity_onboarding);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (AppCompatButton) findViewById(R.id.btn_skip);
        btnNext = (AppCompatButton) findViewById(R.id.btn_next);

        // Layouts of all welcome sliders
        layouts = new int[]{
                R.layout.screen1,
                R.layout.screen2,
                R.layout.screen3,
                R.layout.screen4};

        // Adding bottom dots
        addBottomDots(0);

        // Making the notification bar transparent
        makeStatusBarTransparent();

        // ViewPager Adapter
        myViewPagerAdapter = new MyViewPagerAdapter(this, layouts);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        // Skip the Onborading Activity
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSplashScreen();
            }
        });

        // Move to the next intro slide, if it exists
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking for last page
                // If last slide -> SplashActivity will be launched
                int next = getItem();
                if (next < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(next);
                } else {
                    launchSplashScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
    private int getItem() {
        return viewPager.getCurrentItem() + 1;
    }

    // ViewPager Change Listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            // Changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    // Making notification bar transparent
    private void makeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    // Launch Splash Screen
    private void launchSplashScreen() {
        preferences.setFirstTimeLaunch(false);
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        finish();
    }
}