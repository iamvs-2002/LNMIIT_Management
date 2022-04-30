package in.ac.lnmiit.management.Modules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import in.ac.lnmiit.management.Modules.Classes.StaffLeaveManagement.ViewPager2FragmentAdapter;
import in.ac.lnmiit.management.Modules.Classes.StaffLeaveManagement.applyleaves_bottomsheet;
import in.ac.lnmiit.management.R;

public class StaffLeaveManagement extends AppCompatActivity {

    private Toolbar toolbar;

    ViewPager2FragmentAdapter viewPager2FragmentAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    private String[] titles = new String[]{"ALL", "Sick", "CL"};

    FloatingActionButton fabapplyleave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_leave_management);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Staff Leave Management");
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tabbar);
        viewPager2FragmentAdapter = new ViewPager2FragmentAdapter(this);
        viewPager2 = findViewById(R.id.view_pager2);
        viewPager2.setAdapter(viewPager2FragmentAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(titles[position]))).attach();

        fabapplyleave = findViewById(R.id.fabapplyleave);
        fabapplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyleaves_bottomsheet applyleaves_bottomsheet = new applyleaves_bottomsheet();
                applyleaves_bottomsheet.show(getSupportFragmentManager(), applyleaves_bottomsheet.getTag());
            }
        });

    }


}