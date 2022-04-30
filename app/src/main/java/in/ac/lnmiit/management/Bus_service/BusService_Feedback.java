package in.ac.lnmiit.management.Bus_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.ac.lnmiit.management.Modules.BusService;
import in.ac.lnmiit.management.R;

public class BusService_Feedback extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_service_feedback);
        Context context = getApplicationContext();
        button = findViewById(R.id.btnSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Your feedback has been recorded!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(BusService_Feedback.this, BusService.class);
                BusService_Feedback.this.startActivity(i);
                finish();
            }
        });
    }
}