package in.ac.lnmiit.management.Modules;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import in.ac.lnmiit.management.R;

public class TimeTableGeneration extends AppCompatActivity {

    private Toolbar toolbar;
    String[] semesters = {"1", "2", "3", "4", "5", "6", "7", "8"};
    String[] programs = {"UG", "PG", "PhD"};
    Spinner semestersSpinner, programsSpinner;
    AppCompatButton generateTimeTablebtn, historybtn;
    ImageButton selectexcelfilebtn;
    TextView excelFileName;
    static boolean isFileSelected;
    ProgressBar timetableProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_generation);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Time Table");
        setSupportActionBar(toolbar);

        semestersSpinner = findViewById(R.id.semester_spinner);
        programsSpinner = findViewById(R.id.program_spinner);
        generateTimeTablebtn = findViewById(R.id.generateTimeTablebtn);
        historybtn = findViewById(R.id.historybtn);
        selectexcelfilebtn = findViewById(R.id.selectexcelfilebtn);
        excelFileName = findViewById(R.id.excelFileName);
        timetableProgressBar = findViewById(R.id.timetableProgressBar);

        //Creating the ArrayAdapter instance having the semesters name list
        ArrayAdapter semestersAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,semesters);
        semestersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        semestersSpinner.setAdapter(semestersAdapter);

        //Creating the ArrayAdapter instance having the program name list
        ArrayAdapter programsAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,programs);
        programsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        programsSpinner.setAdapter(programsAdapter);

        selectexcelfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timetableProgressBar.setVisibility(View.VISIBLE);
                selectfile();
                timetableProgressBar.setVisibility(View.GONE);
            }
        });

        generateTimeTablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(semestersSpinner.getSelectedItem() == null){
                    Toast.makeText(TimeTableGeneration.this, "Kindly select a semester.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(programsSpinner.getSelectedItem() == null){
                    Toast.makeText(TimeTableGeneration.this, "Kindly select a program.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isFileSelected){
                    Toast.makeText(TimeTableGeneration.this, "Kindly select the excel file.", Toast.LENGTH_SHORT).show();
                    return;
                }
                timetableProgressBar.setVisibility(View.VISIBLE);
                String sem = semestersSpinner.getSelectedItem().toString();
                String prog = programsSpinner.getSelectedItem().toString();
                Toast.makeText(TimeTableGeneration.this, "Generating time table for "+prog+" "+sem+".", Toast.LENGTH_SHORT).show();
                timetableProgressBar.setVisibility(View.GONE);
            }
        });

        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show history in an alert dialog box
                Toast.makeText(TimeTableGeneration.this, "History button clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void selectfile() {
        // select the file from the file storage
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select timetable file..."), 102);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                String filepath = data.getData().getPath();
                // If excel file then only select the file
                if (filepath.endsWith(".xlsx") || filepath.endsWith(".xls")) {
                    isFileSelected = true;
                    excelFileName.setText(filepath);
                    Toast.makeText(this, "File selected successfully.", Toast.LENGTH_SHORT).show();
                }
                // else show the error
                else {
                    Toast.makeText(this, "Error. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}