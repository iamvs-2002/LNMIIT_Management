package in.ac.lnmiit.management.Modules;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.*;
import java.util.*;

import in.ac.lnmiit.management.Modules.Classes.Graph;
import in.ac.lnmiit.management.R;

public class TimeTableGeneration extends AppCompatActivity {

    private Toolbar toolbar;
    String[] semesters = {"Odd", "Even"};
    String[] programs = {"UG", "PG", "PhD"};
    Spinner semestersSpinner, programsSpinner;
    AppCompatButton generateTimeTablebtn, historybtn;
    ImageButton selectexcelfilebtn;
    TextView excelFileName,timetable_year_tv;
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
        timetable_year_tv = findViewById(R.id.timetable_year_tv);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        timetable_year_tv.setText(year+" - "+(year+1));

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

                // If excel file then only select the file
                if (!filename.endsWith(".xlsx") && !filename.endsWith(".xls")) {
                    Toast.makeText(this, "Error. Please try again later.", Toast.LENGTH_LONG).show();
                    return;
                }

                File myFile = new File(uri.getPath());

                Log.e("TAG", "Path: " + uri.getPath());
                Log.e("TAG", "Exists: " + myFile.exists());

                isFileSelected = true;
                excelFileName.setText(filename);
                file = myFile;
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
            int[] res = readExcelFile(file);

            int row = res[0];
            int col = res[1];
            int v = col; // number of subjects

            Graph graph = new Graph(v);

            int cliq = Graph.Findclique(v);
            Log.e("TAG", "Total number of clique: "+cliq);
            Toast.makeText(TimeTableGeneration.this, "Total number of clique: " + cliq, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TAG", "Some error while reading excel file" + e);
            e.printStackTrace();
        }
        timetableProgressBar.setVisibility(View.GONE);
    }

    private int[] readExcelFile(File file) throws IOException {
        InputStream myInput;
        // initialize asset manager
        AssetManager assetManager = getAssets();
        //  open excel file name as timetable.xls
        myInput = assetManager.open("timetable.xls");
        // Create a POI File System object
        POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

        // FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file

        // Create a POIFSFileSystem object
        // POIFSFileSystem myFileSystem = new POIFSFileSystem(fis);

        // Creating Workbook instance that refers to .xls file
        HSSFWorkbook wb = new HSSFWorkbook(myFileSystem);
        HSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object

        Log.e("TAG", "workbook sheet");
        int row = sheet.getLastRowNum();
        int col = sheet.getRow(1).getLastCellNum();

        int[] res = new int[2];
        res[0] = row;
        res[1] = col;
        return res;
    }
}