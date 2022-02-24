package in.ac.lnmiit.management.Modules;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.*;

import in.ac.lnmiit.management.R;

public class TimeTableGeneration extends AppCompatActivity {

    private Toolbar toolbar;
    String[] semesters = {"Odd", "Even"};
    String[] programs = {"UG", "PG", "PhD"};
    Spinner semestersSpinner, programsSpinner;
    AppCompatButton generateTimeTablebtn, savebtn;
    ImageButton selectexcelfilebtn;
    TextView excelFileName, timetable_year_tv, timetable_cliques_count_tv, timetable_result_tv, timetable_subjects_count_tv;
    static boolean isFileSelected;
    ProgressBar timetableProgressBar;
    static File file;
    ScrollView timetable_result_view;
    static HSSFSheet sheet;
    static int[] studentCount;
    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };
    private int reqCode = 11;

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
        savebtn = findViewById(R.id.savebtn);
        selectexcelfilebtn = findViewById(R.id.selectexcelfilebtn);
        excelFileName = findViewById(R.id.excelFileName);
        timetableProgressBar = findViewById(R.id.timetableProgressBar);
        timetable_year_tv = findViewById(R.id.timetable_year_tv);
        timetable_cliques_count_tv = findViewById(R.id.timetable_cliques_count_tv);
        timetable_result_tv = findViewById(R.id.timetable_result_tv);
        timetable_result_view = findViewById(R.id.timetable_result_view);
        timetable_subjects_count_tv = findViewById(R.id.timetable_subjects_count_tv);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        timetable_year_tv.setText(year + " - " + (year + 1));

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
                selectfile();
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
                    Toast.makeText(TimeTableGeneration.this, "Kindly select an excel file.", Toast.LENGTH_SHORT).show();
                    return;
                }
                timetableProgressBar.setVisibility(View.VISIBLE);
                String sem = semestersSpinner.getSelectedItem().toString();
                String prog = programsSpinner.getSelectedItem().toString();

                findCliques(file);

                timetableProgressBar.setVisibility(View.GONE);
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show history in an alert dialog box
                if (Graph.res==null || Graph.res.toString().isEmpty()){
                    Toast.makeText(TimeTableGeneration.this, "Kindly generate the time table first.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(TimeTableGeneration.this, "Save button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void downloadFile() {
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/imagestore-b2432.appspot.com/o/Nature.jpg?alt=media&token=07d95162-45f8-424e-9658-8f9022485930");
//
//        ProgressDialog  pd = new ProgressDialog(this);
//        pd.setTitle("Nature.jpg");
//        pd.setMessage("Downloading Please Wait!");
//        pd.setIndeterminate(true);
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pd.show();
//
//
//        final File rootPath = new File(Environment.getExternalStorageDirectory(), "MADBO DOWNLOADS");
//
//        if (!rootPath.exists()) {
//            rootPath.mkdirs();
//        }
//
//
//        final File localFile = new File(rootPath, "Nature.jpg");
//
//        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener <FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
//
//                if (!isVisible()){
//                    return;
//                }
//
//                if (localFile.canRead()){
//
//                    pd.dismiss();
//                }
//
//                Toast.makeText(this, "Download Completed", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "Internal storage/MADBO/Nature.jpg", Toast.LENGTH_LONG).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
//                Toast.makeText(this, "Download Incompleted", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
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

                String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

                // String path = getFilesDir().getAbsolutePath();

                File myFile = new File(downloadsPath + "/"+ filename);

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
        // Create the graph given in the above figure
        try {
            Log.e("TAG", "Finding Cliques");

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem;

            try{
                Log.e("TAG", "Downloads file");
                Log.e("TAG", file.exists()+"");
                FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
                myFileSystem = new POIFSFileSystem(fis);
            }
            catch (Exception e){
                Log.e("TAG", "Exception: "+e);
                Log.e("TAG", "Assets file");
                InputStream myInput;
                // initialize asset manager
                AssetManager assetManager = getAssets();
                // Open excel file name as timetable.xls
                myInput = assetManager.open("timetable.xls");
                // Create a POI File System object
                myFileSystem = new POIFSFileSystem(myInput);
            }

            // Creating Workbook instance that refers to .xls file
            HSSFWorkbook wb = new HSSFWorkbook(myFileSystem);
            sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object

            Log.e("TAG", "workbook sheet");
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(1).getLastCellNum();

            studentCount = new int[cols];

            Map<Integer, LinkedHashSet<String>> courses = new HashMap<>();

            for (int i = 1; i <= cols; i++) {
                courses.put(i, new LinkedHashSet<>());
            }
            for (int r = 1; r <= rows; r++) {
                HSSFRow row = sheet.getRow(r);
                for (int c = 0; c < cols; c++) {
                    HSSFCell cell = row.getCell((short) c);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                studentCount[c]++;
                                String rollNumber = row.getCell((short) c).getStringCellValue();
                                courses.get(c + 1).add(rollNumber);
                                break;

                            default:
                                break;
                        }
                    }
                }
            }

            int v = cols;
            Graph graph = new Graph(v);

            for (int i = 1; i < v; i++) {
                LinkedHashSet<String> setx = courses.get(i);
                for (int j = i + 1; j <= v; j++) {
                    LinkedHashSet<String> setY = courses.get(j);
                    Iterator<String> iteratorx = setx.iterator();
                    Map<String, Integer> compare = new HashMap<>();

                    while (iteratorx.hasNext()) {
                        compare.put(iteratorx.next(), 0);
                    }

                    Iterator<String> iteratorY = setY.iterator();
                    boolean flag = false;

                    while (iteratorY.hasNext()) {
                        String s = iteratorY.next();
                        if (compare.containsKey(s)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        graph.addEdge(i, j);
                    }
                }
            }

            int cliq = Graph.findClique(v);

            timetable_subjects_count_tv.setText(v+"");
            timetable_cliques_count_tv.setText(cliq+"");
            timetable_result_tv.setText(Graph.res.toString());
            timetable_result_view.setVisibility(View.VISIBLE);

            Log.e("TAG", "Total number of clique: " + cliq);
            // Toast.makeText(TimeTableGeneration.this, "Total number of clique: " + cliq, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("TAG", "Some error while reading excel file" + e);
            e.printStackTrace();
        }
        timetableProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!isPermissionGranted())
            askPermissions();
    }
    private boolean isPermissionGranted(){
        for(String permission : permissions){
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
    private void askPermissions(){
        ActivityCompat.requestPermissions(this, permissions, reqCode);
    }
}


class Graph {
    // result containing the number of cliques, subjects and total students in each clique
    static StringBuilder res;
    // TreeSet is used to get clear understanding of graph.
    static HashMap<Integer, TreeSet<Integer>> graph;
    static HSSFRow header;

    // Graph Constructor
    public Graph(int v) {
        graph = new HashMap<>();
        header = TimeTableGeneration.sheet.getRow(0);
        res = new StringBuilder();
        for (int i = 1; i <= v; i++) {
            graph.put(i, new TreeSet<>());
        }
    }

    // Adds an edge to an undirected graph
    public void addEdge(int src, int dest) {
        // Add an edge from src to dest into the set
        graph.get(src).add(dest);

        // Since graph is undirected, add an edge
        // from dest to src into the set
        graph.get(dest).add(src);
    }

    public static boolean[] visited;
    static Map<Integer, Integer> V = new HashMap<>();

    public static int findClique(int no_of_subject) {
        int v = no_of_subject;
        visited = new boolean[v + 1];
        int subject = 1;
        int count = 0;
        for (int i = 1; i <= no_of_subject; i++) {
            if (graph.containsKey(i))
                V.put(i, graph.get(i).size());
        }

        while (subject <= no_of_subject) {
            if (visited[subject]) {
                subject++;
            } else {
                Stack<Integer> clique = new Stack<Integer>();
                makeClique(subject, clique);
                count++;
                subject++;
                res.append("Clique ").append(count).append(": ");
                Log.e("TAG", "clique " + count + ":");
                int totalStudents = 0;
                while (!clique.isEmpty()) {
                    int c = clique.pop();
                    int students = TimeTableGeneration.studentCount[c-1];
                    totalStudents+=students;
                    String subj = header.getCell((short) (c-1)).getStringCellValue();
                    res.append(subj).append("(").append(String.valueOf(students)).append(")").append(" ");
                    Log.e("TAG",subj + " ");
                }
                res.append("\n");
                res.append("Total Students: ").append(totalStudents);
                res.append("\n");
                res.append("\n");
                Log.e("TAG", "\n");
                //update V
                for (int i = 1; i <= no_of_subject; i++) {
                    if (graph.containsKey(i) && !visited[i])
                        V.put(i, graph.get(i).size());
                }
            }
        }
        return count;
    }

    public static void makeClique(int subject, Stack<Integer> clique) {
        clique.add(subject);
        visited[subject] = true;
        Map<Integer, Integer> adjacent = findAdjacent(subject);

        Map<Integer, Integer> newV = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : adjacent.entrySet()) {
            int x = entry.getKey();
            if (V.containsKey(x)) {
                newV.put(x, entry.getValue());
            }

        }
        V = newV;
        if (!V.isEmpty()) {
            int max_degree_node = Integer.MIN_VALUE;
            for (Map.Entry<Integer, Integer> entry : V.entrySet()) {
                if (entry.getValue() > max_degree_node) {
                    max_degree_node = entry.getKey();
                }
            }

            makeClique(max_degree_node, clique);
        }

    }

    public static Map<Integer, Integer> findAdjacent(int subject) {
        Map<Integer, Integer> adjacent = new HashMap<>();
        if (graph.containsKey(subject)) {
            Iterator<Integer> set = graph.get(subject).iterator();

            while (set.hasNext()) {
                int x = set.next();
                adjacent.put(x, graph.get(x).size());
            }
        }
        return adjacent;
    }
}