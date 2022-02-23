package in.ac.lnmiit.management.Modules;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.documentfile.provider.DocumentFile;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;

import in.ac.lnmiit.management.Modules.Classes.Graph;
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
    static File file;

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
        ArrayAdapter semestersAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, semesters);
        semestersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        semestersSpinner.setAdapter(semestersAdapter);

        //Creating the ArrayAdapter instance having the program name list
        ArrayAdapter programsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, programs);
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
                if (semestersSpinner.getSelectedItem() == null) {
                    Toast.makeText(TimeTableGeneration.this, "Kindly select a semester.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (programsSpinner.getSelectedItem() == null) {
                    Toast.makeText(TimeTableGeneration.this, "Kindly select a program.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isFileSelected) {
                    Toast.makeText(TimeTableGeneration.this, "Kindly select the excel file.", Toast.LENGTH_SHORT).show();
                    return;
                }
                timetableProgressBar.setVisibility(View.VISIBLE);
                String sem = semestersSpinner.getSelectedItem().toString();
                String prog = programsSpinner.getSelectedItem().toString();
                findCliques(file);
                Toast.makeText(TimeTableGeneration.this, "Generating time table for " + prog + " " + sem + ".", Toast.LENGTH_SHORT).show();
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
        intent.setType("application/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select timetable file..."), 102);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            Log.e("TAG", "Req code verified");
            if (resultCode == RESULT_OK) {

                Uri uri = data.getData();
                String filename = getFileName(uri);

                File myFile = new File(uri.getPath());

                Log.e("TAG", "Path: " + uri.getPath());
                Log.e("TAG", "Exists: " + myFile.exists());

                // If excel file then only select the file
                if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
                    isFileSelected = true;
                    excelFileName.setText(filename);
                    file = myFile;
                }
                // else show the error
                else {
                    Toast.makeText(this, "Error. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

        cursor.close();

        return fileName;
    }
    void findCliques(File file) {
        timetableProgressBar.setVisibility(View.VISIBLE);
        Map<String, TreeSet<String>> courses = new HashMap<>();
        // Create the graph given in the above figure
        try {
            Log.e("TAG", "Finding Cliques");
            Log.e("TAG", file.exists() + "");
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
            Log.e("TAG", "input stream");

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(fis);
            Log.e("TAG", "File system");

            // Creating Workbook instance that refers to .xlsx file
            HSSFWorkbook wb = new HSSFWorkbook(myFileSystem);
            HSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object

            Log.e("TAG", "workbook sheet");
            int row = sheet.getLastRowNum();
            int col = sheet.getRow(1).getLastCellNum();

            int v = col; // number of subjects
            Graph graph = new Graph(v);
            Log.e("TAG", "Finding Cliques 2");
            Toast.makeText(TimeTableGeneration.this, "Total number of clique: " + Graph.Findclique(v), Toast.LENGTH_SHORT).show();
            // System.out.print("Total number of clique: " + Graph.Findclique(v));
        } catch (Exception e) {
            Log.e("TAG", "Some error while reading excel file" + e);
            e.printStackTrace();
        }
        timetableProgressBar.setVisibility(View.GONE);
    }
}