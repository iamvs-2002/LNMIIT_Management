package in.ac.lnmiit.management.Modules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import in.ac.lnmiit.management.Modules.Classes.Bus_service.BusServiceUserViewSchedule;
import in.ac.lnmiit.management.Modules.Classes.Bus_service.BusService_Feedback;
import in.ac.lnmiit.management.Modules.Classes.Bus_service.bus_booking;
import in.ac.lnmiit.management.Modules.Classes.Bus_service.bus_service_past_booking;
import in.ac.lnmiit.management.R;

public class BusService extends AppCompatActivity {

    private Toolbar toolbar;
    CardView cardView1_user;
    CardView cardView2_user;
    CardView cardView3_user;
    CardView cardView4_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_service);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("BusService");
        setSupportActionBar(toolbar);
        Context context = getApplicationContext();
        cardView1_user = findViewById(R.id.bus_service_home_page_card1);
        cardView2_user = findViewById(R.id.bus_service_home_page_card2);
        cardView3_user = findViewById(R.id.bus_service_home_page_card3);
        cardView4_user = findViewById(R.id.bus_service_home_page_card4);
        cardView1_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusService.this, bus_booking.class);
                BusService.this.startActivity(i);
            }
        });
        cardView2_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusService.this, bus_service_past_booking.class);
                BusService.this.startActivity(i);
            }
        });
        cardView3_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusService.this, BusServiceUserViewSchedule.class);
                BusService.this.startActivity(i);
            }
        });
        cardView4_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusService.this, BusService_Feedback.class);
                BusService.this.startActivity(i);
            }
        });
    }
}