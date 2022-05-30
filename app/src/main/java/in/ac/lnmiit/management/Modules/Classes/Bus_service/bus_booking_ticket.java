package in.ac.lnmiit.management.Modules.Classes.Bus_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.ac.lnmiit.management.Modules.BusService;
import in.ac.lnmiit.management.R;

public class bus_booking_ticket extends AppCompatActivity {
    Button button;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_booking_ticket);
        button = findViewById(R.id.bookbutton);
        context = getApplicationContext();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bus_booking_ticket.this, BusService.class);
                Toast.makeText(context, "Request Sent!",Toast.LENGTH_LONG).show();
                bus_booking_ticket.this.startActivity(i);
                finish();
            }
        });
    }
}