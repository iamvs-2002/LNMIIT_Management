package in.ac.lnmiit.management.Modules.Classes.Bus_service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import in.ac.lnmiit.management.R;

public class BusServiceUserViewSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_service_user_view_schedule);
        Spinner spinner = findViewById(R.id.spinner_bus_service_user_view_schedule);
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

        final String[] day = {"Sunday"};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show(); // remove this line
                day[0] = tutorialsName;
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        // fetch data   }
    }
}