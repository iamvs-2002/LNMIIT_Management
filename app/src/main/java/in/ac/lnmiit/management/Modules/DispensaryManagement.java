package in.ac.lnmiit.management.Modules;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentTimingAdapter;
import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentTimingModel;
import in.ac.lnmiit.management.R;

public class DispensaryManagement extends AppCompatActivity {

    private Toolbar toolbar;
    LinearLayout studentLayout, doctorLayout;
    CardView student_appointment, student_medicalcertificate, student_equipmentstatus;
    CardView doctor_appointment, doctor_medicalcertificate, doctor_equipmentstatus, doctor_stockstatus;

    RecyclerView student_dispensary_card_appointment_timing_recyclerView, doctor_dispensary_card_appointment_timing_recyclerView;
    static List<AppointmentTimingModel> appointmentTimingModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensary_management);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dispensary Management");
        setSupportActionBar(toolbar);

        studentLayout = findViewById(R.id.student_dispensary_layout);
        doctorLayout = findViewById(R.id.doctor_dispensary_layout);

        student_appointment = findViewById(R.id.student_dispensary_card_appointment);
        student_equipmentstatus = findViewById(R.id.student_dispensary_card_equipmentstatus);
        student_medicalcertificate = findViewById(R.id.student_dispensary_card_medicalcertificate);
        doctor_appointment = findViewById(R.id.doctor_dispensary_card_appointment);
        doctor_equipmentstatus = findViewById(R.id.doctor_dispensary_card_equipmentstatus);
        doctor_medicalcertificate = findViewById(R.id.doctor_dispensary_card_medicalcertificate);
        doctor_stockstatus = findViewById(R.id.doctor_dispensary_card_stockstatus);
        student_dispensary_card_appointment_timing_recyclerView = findViewById(R.id.student_dispensary_card_appointment_timing_recyclerView);
        doctor_dispensary_card_appointment_timing_recyclerView = findViewById(R.id.doctor_dispensary_card_appointment_timing_recyclerView);

        appointmentTimingModelList = new ArrayList<>();
        appointmentTimingModelList = fillTimings();

        // set adapter
        AppointmentTimingAdapter adapter = new AppointmentTimingAdapter(this, appointmentTimingModelList);
        student_dispensary_card_appointment_timing_recyclerView.setHasFixedSize(true);
        student_dispensary_card_appointment_timing_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        student_dispensary_card_appointment_timing_recyclerView.setAdapter(adapter);

        doctor_dispensary_card_appointment_timing_recyclerView.setHasFixedSize(true);
        doctor_dispensary_card_appointment_timing_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctor_dispensary_card_appointment_timing_recyclerView.setAdapter(adapter);

        student_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(0);
            }
        });
        student_medicalcertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(1);
            }
        });
        student_equipmentstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(2);
            }
        });
        doctor_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(10);
            }
        });
        doctor_medicalcertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(11);
            }
        });
        doctor_equipmentstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(12);
            }
        });
        doctor_stockstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(13);
            }
        });
    }

    private List<AppointmentTimingModel> fillTimings() {
        List<AppointmentTimingModel> list = new ArrayList<>();
        list.add(new AppointmentTimingModel("Doctor A", "10:00 - 14:00"));
        list.add(new AppointmentTimingModel("Doctor B", "10:00 - 14:00"));
        list.add(new AppointmentTimingModel("Doctor C", "10:00 - 14:00"));
        list.add(new AppointmentTimingModel("Doctor D", "10:00 - 14:00"));

        return list;
    }

    private void showAlertDialogBox(int n) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name");
        builder.setCancelable(false);
        View customLayout;
        switch (n) {
            case 0:
                Toast.makeText(this, "Student Appointment", Toast.LENGTH_SHORT).show();
                // set the custom layout
                builder.setTitle("Book an Appointment");
                customLayout = getLayoutInflater().inflate(R.layout.student_appointment, null);
                builder.setView(customLayout);

                RadioGroup studentAppointmentRadioGroup = customLayout.findViewById(R.id.student_appointment_radioGroup);
                studentAppointmentRadioGroup.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < appointmentTimingModelList.size(); i++) {
                    RadioButton rdbtn = new RadioButton(this);
                    // rdbtn.setId(View.generateViewId());
                    rdbtn.setId(i);
                    rdbtn.setText(appointmentTimingModelList.get(rdbtn.getId()).getDoctorTiming());
                    studentAppointmentRadioGroup.addView(rdbtn);
                }
                TextInputEditText studentAppointmentMedicalIssueEditText = customLayout.findViewById(R.id.student_appointment_medical_issue_tv);

                // add the button
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedId = studentAppointmentRadioGroup.getCheckedRadioButtonId();
                        String medicalIssue = studentAppointmentMedicalIssueEditText.getText().toString();
                        if(selectedId==-1){
                            Toast.makeText(DispensaryManagement.this, "Select a timing", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(DispensaryManagement.this, "Medical Issue: "+medicalIssue+"\nSelected Timing: "+appointmentTimingModelList.get(selectedId).getDoctorTiming(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case 1:
                Toast.makeText(this, "Student Medical Certificate", Toast.LENGTH_SHORT).show();
                builder.setTitle("Request Medical Certificate");
                customLayout = getLayoutInflater().inflate(R.layout.student_medicalcertificate, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case 2:
                Toast.makeText(this, "Student Equipment Status", Toast.LENGTH_SHORT).show();
                builder.setTitle("Equipment Status");
                customLayout = getLayoutInflater().inflate(R.layout.student_equipmentstatus, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case 10:
                Toast.makeText(this, "Doctor Appointment", Toast.LENGTH_SHORT).show();
                builder.setTitle("Appointment Status");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_appointment, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case 11:
                Toast.makeText(this, "Doctor Medical Certificate", Toast.LENGTH_SHORT).show();
                builder.setTitle("Approve/Reject Medical Certificate Requests");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_medicalcertificate, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case 12:
                Toast.makeText(this, "Doctor Equipment Status", Toast.LENGTH_SHORT).show();
                builder.setTitle("Equipment Status");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_equipmentstatus, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case 13:
                Toast.makeText(this, "Doctor Stock Status", Toast.LENGTH_SHORT).show();
                builder.setTitle("Stock Status");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_stockstatus, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
        }
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dispensarymenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.dispensary_doctor_login_menuoption:
                doctorLayout.setVisibility(View.VISIBLE);
                studentLayout.setVisibility(View.GONE);
                return true;
            case R.id.dispensary_student_login_menuoption:
                doctorLayout.setVisibility(View.GONE);
                studentLayout.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}