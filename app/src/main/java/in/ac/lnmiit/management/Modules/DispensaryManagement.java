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

import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentStatusAdapter;
import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentStatusModel;
import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentTimingAdapter;
import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.AppointmentTimingModel;
import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.MedCertificateStatusAdapter;
import in.ac.lnmiit.management.Modules.Classes.DispensaryManagement.MedCertificateStatusModel;
import in.ac.lnmiit.management.R;

public class DispensaryManagement extends AppCompatActivity {

    private Toolbar toolbar;
    LinearLayout studentLayout, doctorLayout;
    CardView student_appointment, student_medicalcertificate, student_appointment_status, student_medicalcertificate_status;
    CardView doctor_appointment, doctor_medicalcertificate, doctor_stockstatus;

    RecyclerView student_dispensary_card_appointment_timing_recyclerView;
    static List<AppointmentTimingModel> appointmentTimingModelList;
    static boolean isStudent = true;
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

        if (isStudent){
            doctorLayout.setVisibility(View.GONE);
            studentLayout.setVisibility(View.VISIBLE);
        }
        else{
            doctorLayout.setVisibility(View.VISIBLE);
            studentLayout.setVisibility(View.GONE);
        }

        appointmentTimingModelList = new ArrayList<>();
        appointmentTimingModelList = fillTimings();
        // set adapter
        AppointmentTimingAdapter adapter = new AppointmentTimingAdapter(this, appointmentTimingModelList);
        student_dispensary_card_appointment_timing_recyclerView.setHasFixedSize(true);
        student_dispensary_card_appointment_timing_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        student_dispensary_card_appointment_timing_recyclerView.setAdapter(adapter);


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
        // Calender object to get current date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

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

                TextInputEditText student_appointment_date = customLayout.findViewById(R.id.student_appointment_date);
                RadioGroup studentAppointmentRadioGroup = customLayout.findViewById(R.id.student_appointment_radioGroup);
                studentAppointmentRadioGroup.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < appointmentTimingModelList.size(); i++) {
                    RadioButton rdbtn = new RadioButton(this);
                    // rdbtn.setId(View.generateViewId());
                    rdbtn.setId(i);
                    String s = appointmentTimingModelList.get(rdbtn.getId()).getDoctorName()+" ("+appointmentTimingModelList.get(rdbtn.getId()).getDoctorTiming()+")";
                    rdbtn.setText(s);
                    studentAppointmentRadioGroup.addView(rdbtn);
                }
                TextInputEditText studentAppointmentMedicalIssueEditText = customLayout.findViewById(R.id.student_appointment_medical_issue_tv);

                student_appointment_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(DispensaryManagement.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        student_appointment_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                // add the button
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedId = studentAppointmentRadioGroup.getCheckedRadioButtonId();
                        String medicalIssue = studentAppointmentMedicalIssueEditText.getText().toString();
                        String date = student_appointment_date.getText().toString();
                        if(selectedId==-1 || date.isEmpty() || medicalIssue.isEmpty()){
                            Toast.makeText(DispensaryManagement.this, "No field can be empty", Toast.LENGTH_SHORT).show();
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

                List<AppointmentStatusModel> appointmentStatusModelList = new ArrayList<>();
                appointmentStatusModelList = getAppointmentStatusList();

                RecyclerView student_dispensary_appointment_status_recyclerView = customLayout.findViewById(R.id.student_dispensary_appointment_status_recyclerView);
                AppointmentStatusAdapter adapter = new AppointmentStatusAdapter(this, appointmentStatusModelList, isStudent);
                student_dispensary_appointment_status_recyclerView.setHasFixedSize(true);
                student_dispensary_appointment_status_recyclerView.setLayoutManager(new LinearLayoutManager(this));
                student_dispensary_appointment_status_recyclerView.setAdapter(adapter);

                break;
            case 3:
                builder.setTitle("Medical Certificate Status");
                customLayout = getLayoutInflater().inflate(R.layout.student_medicalcertificate_status, null);
                builder.setView(customLayout);

                List<MedCertificateStatusModel> medCertificateStatusModelList = new ArrayList<>();
                medCertificateStatusModelList = getMedCertificateStatusList();

                RecyclerView student_dispensary_medcert_status_recyclerView = customLayout.findViewById(R.id.student_dispensary_medcert_status_recyclerView);
                MedCertificateStatusAdapter adapterb = new MedCertificateStatusAdapter(this, medCertificateStatusModelList, isStudent);
                student_dispensary_medcert_status_recyclerView.setHasFixedSize(true);
                student_dispensary_medcert_status_recyclerView.setLayoutManager(new LinearLayoutManager(this));
                student_dispensary_medcert_status_recyclerView.setAdapter(adapterb);
                break;
            case 10:
                // Toast.makeText(this, "Doctor Appointment", Toast.LENGTH_SHORT).show();
                builder.setTitle("Manage Appointments");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_appointment, null);
                builder.setView(customLayout);

                List<AppointmentStatusModel> appointmentStatusModelList2 = new ArrayList<>();
                appointmentStatusModelList2 = getAppointmentStatusList();

                RecyclerView doctor_dispensary_appointment_status_recyclerView = customLayout.findViewById(R.id.doctor_dispensary_appointment_status_recyclerView);
                AppointmentStatusAdapter adapter2 = new AppointmentStatusAdapter(this, appointmentStatusModelList2, isStudent);
                doctor_dispensary_appointment_status_recyclerView.setHasFixedSize(true);
                doctor_dispensary_appointment_status_recyclerView.setLayoutManager(new LinearLayoutManager(this));
                doctor_dispensary_appointment_status_recyclerView.setAdapter(adapter2);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(DispensaryManagement.this, "Update DB", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 11:
                // Toast.makeText(this, "Doctor Medical Certificate", Toast.LENGTH_SHORT).show();
                builder.setTitle("Manage Medical Certificates");
                customLayout = getLayoutInflater().inflate(R.layout.doctor_medicalcertificate, null);
                builder.setView(customLayout);
                List<MedCertificateStatusModel> medCertificateStatusModelList2 = new ArrayList<>();
                medCertificateStatusModelList = getMedCertificateStatusList();

                RecyclerView doctor_dispensary_medcert_status_recyclerView = customLayout.findViewById(R.id.doctor_dispensary_medcert_status_recyclerView);
                MedCertificateStatusAdapter adapterb2 = new MedCertificateStatusAdapter(this, medCertificateStatusModelList, isStudent);
                doctor_dispensary_medcert_status_recyclerView.setHasFixedSize(true);
                doctor_dispensary_medcert_status_recyclerView.setLayoutManager(new LinearLayoutManager(this));
                doctor_dispensary_medcert_status_recyclerView.setAdapter(adapterb2);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(DispensaryManagement.this, "Update DB", Toast.LENGTH_SHORT).show();
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

    private List<MedCertificateStatusModel> getMedCertificateStatusList() {
        List<MedCertificateStatusModel> list = new ArrayList<>();
        list.add(new MedCertificateStatusModel("Name", "Email", "StartDate", "EndDate", "Days", "Issue", false));
        list.add(new MedCertificateStatusModel("Name", "Email", "StartDate", "EndDate", "Days", "Issue", false));
        list.add(new MedCertificateStatusModel("Name", "Email", "StartDate", "EndDate", "Days", "Issue", true));
        list.add(new MedCertificateStatusModel("Name", "Email", "StartDate", "EndDate", "Days", "Issue", true));
        list.add(new MedCertificateStatusModel("Name", "Email", "StartDate", "EndDate", "Days", "Issue", false));

        return list;
    }

    private List<AppointmentStatusModel> getAppointmentStatusList() {
        List<AppointmentStatusModel> list = new ArrayList<>();
        list.add(new AppointmentStatusModel("Name", "Email", "Issue", "Date", "Time", false));
        list.add(new AppointmentStatusModel("Name", "Email", "Issue", "Date", "Time", true));
        list.add(new AppointmentStatusModel("Name", "Email", "Issue", "Date", "Time", true));
        list.add(new AppointmentStatusModel("Name", "Email", "Issue", "Date", "Time", false));
        list.add(new AppointmentStatusModel("Name", "Email", "Issue", "Date", "Time", false));

        return list;
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
                isStudent = false;
                doctorLayout.setVisibility(View.VISIBLE);
                studentLayout.setVisibility(View.GONE);
                return true;
            case R.id.dispensary_student_login_menuoption:
                isStudent = true;
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