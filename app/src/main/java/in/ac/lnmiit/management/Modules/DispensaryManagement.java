package in.ac.lnmiit.management.Modules;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentTimingAdapter;
import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentTimingModel;
import in.ac.lnmiit.management.R;

public class DispensaryManagement extends AppCompatActivity {

    private Toolbar toolbar;
    LinearLayout studentLayout, doctorLayout;
    CardView student_appointment, student_medicalcertificate, student_appointment_status, student_medicalcertificate_status;
    CardView doctor_appointment, doctor_medicalcertificate, doctor_stockstatus;

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
        student_medicalcertificate = findViewById(R.id.student_dispensary_card_medicalcertificate);
        student_appointment_status = findViewById(R.id.student_dispensary_card_appointment_status);
        student_medicalcertificate_status = findViewById(R.id.student_dispensary_card_medicalcertificate_status);
        doctor_appointment = findViewById(R.id.doctor_dispensary_card_appointment);
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
        student_appointment_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(2);
            }
        });
        student_medicalcertificate_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(3);
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
        doctor_stockstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogBox(12);
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
                // Toast.makeText(this, "Student Appointment", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(DispensaryManagement.this, "Kindly select a timing", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(DispensaryManagement.this, "Medical Issue: "+medicalIssue+"\nSelected Timing: "+appointmentTimingModelList.get(selectedId).getDoctorTiming(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case 1:
                // Toast.makeText(this, "Student Medical Certificate", Toast.LENGTH_SHORT).show();
                builder.setTitle("Request Medical Certificate");
                customLayout = getLayoutInflater().inflate(R.layout.student_medicalcertificate, null);
                builder.setView(customLayout);

                TextInputEditText studentMedCertMedicalIssueEditText = customLayout.findViewById(R.id.student_medcert_medical_issue_tv);
                TextInputEditText studentMedCertStartDateEditText = customLayout.findViewById(R.id.student_medcert_start_date);
                TextInputEditText studentMedCertEndDateEditText = customLayout.findViewById(R.id.student_medcert_end_date);

                // Calender object to get the current date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                studentMedCertStartDateEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(DispensaryManagement.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        studentMedCertStartDateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                studentMedCertEndDateEditText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(DispensaryManagement.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        studentMedCertEndDateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String medicalIssue = studentMedCertMedicalIssueEditText.getText().toString();
                        String startDate = studentMedCertStartDateEditText.getText().toString();
                        String endDate = studentMedCertEndDateEditText.getText().toString();

                        long days = findDifference(startDate, endDate);
                        if(days<0){
                            Toast.makeText(DispensaryManagement.this, "End date cannot be before Start date!", Toast.LENGTH_SHORT).show();
                        }
                        if(startDate.isEmpty() || endDate.isEmpty() || medicalIssue.isEmpty()){
                            Toast.makeText(DispensaryManagement.this, "No field can be empty!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(DispensaryManagement.this, "Medical Issue: "+medicalIssue+"\nNumber of Days: "+days, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case 2:
                builder.setTitle("Appointment Status");
                customLayout = getLayoutInflater().inflate(R.layout.student_appointment_status, null);
                builder.setView(customLayout);
                break;
            case 3:
                builder.setTitle("Medical Certificate Status");
                customLayout = getLayoutInflater().inflate(R.layout.student_medicalcertificate_status, null);
                builder.setView(customLayout);
                break;
            case 10:
                // Toast.makeText(this, "Doctor Appointment", Toast.LENGTH_SHORT).show();
                builder.setTitle("Manage Appointments");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_appointment, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case 11:
                // Toast.makeText(this, "Doctor Medical Certificate", Toast.LENGTH_SHORT).show();
                builder.setTitle("Manage Medical Certificates");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_medicalcertificate, null);
                builder.setView(customLayout);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case 12:
                // Toast.makeText(this, "Doctor Stock Status", Toast.LENGTH_SHORT).show();
                builder.setTitle("Stock Status");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_stockstatus, null);
                builder.setView(customLayout);
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


    static long findDifference(String start_date, String end_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        long difference_In_Days = 0;
        try {
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            long difference_In_Time = d2.getTime() - d1.getTime();
            difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return difference_In_Days;
    }
}