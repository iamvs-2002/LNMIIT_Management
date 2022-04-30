package in.ac.lnmiit.management.Bus_service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import in.ac.lnmiit.management.Modules.BusService;
import in.ac.lnmiit.management.R;

public class bus_booking extends AppCompatActivity {
    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;
    CardView card5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_booking);
        Spinner spinner = findViewById(R.id.spinner_bus_service_user_booking);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Monday");
        arrayList.add("Tuesday");
        arrayList.add("Wednesday");
        arrayList.add("Thursday");
        arrayList.add("Friday");
        arrayList.add("Saturday");
        arrayList.add("Sunday");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        card1 = findViewById(R.id.book_card1);
        card2 = findViewById(R.id.book_card2);
        card3 = findViewById(R.id.book_card3);
        card4 = findViewById(R.id.book_card4);
        card5 = findViewById(R.id.book_card5);
        final String[] day = {"Sunday"};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
               // Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show(); // remove this line
                day[0] = tutorialsName;
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bus_booking.this, bus_booking_ticket.class);
                bus_booking.this.startActivity(i);
                finish();
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bus_booking.this, bus_booking_ticket.class);
                bus_booking.this.startActivity(i);
                finish();
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bus_booking.this, bus_booking_ticket.class);
                bus_booking.this.startActivity(i);
                finish();
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bus_booking.this, bus_booking_ticket.class);
                bus_booking.this.startActivity(i);
                finish();
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(bus_booking.this, bus_booking_ticket.class);
                bus_booking.this.startActivity(i);
                finish();
            }
        });
    }
}