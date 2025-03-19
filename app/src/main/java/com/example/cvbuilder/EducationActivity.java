package com.example.cvbuilder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EducationActivity extends AppCompatActivity {

    private EditText etSchoolName;
    private EditText etDegree;
    private EditText etFieldOfStudy;
    private EditText etStartDate;
    private EditText etEndDate;
    private EditText etGPA;
    private EditText etLocation;
    private EditText etDescription;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_education);

        // Initialize UI elements
        initUI();

        // Load existing data if available
        loadExistingData();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        etSchoolName = findViewById(R.id.etSchoolName);
        etDegree = findViewById(R.id.etDegree);
        etFieldOfStudy = findViewById(R.id.etFieldOfStudy);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etGPA = findViewById(R.id.etGPA);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadExistingData() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        etSchoolName.setText(prefs.getString("education_school_name", ""));
        etDegree.setText(prefs.getString("education_degree", ""));
        etFieldOfStudy.setText(prefs.getString("education_field_of_study", ""));
        etStartDate.setText(prefs.getString("education_start_date", ""));
        etEndDate.setText(prefs.getString("education_end_date", ""));
        etGPA.setText(prefs.getString("education_gpa", ""));
        etLocation.setText(prefs.getString("education_location", ""));
        etDescription.setText(prefs.getString("education_description", ""));
    }

    private void setupListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

                // Return result to Home activity
                setResult(RESULT_OK);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just close the activity without saving
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void saveData() {
        // Get data from EditText fields
        String schoolName = etSchoolName.getText().toString().trim();
        String degree = etDegree.getText().toString().trim();
        String fieldOfStudy = etFieldOfStudy.getText().toString().trim();
        String startDate = etStartDate.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        String gpa = etGPA.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Validate required fields
        if (schoolName.isEmpty()) {
            Toast.makeText(this, "Please enter school/university name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (degree.isEmpty()) {
            Toast.makeText(this, "Please enter degree/certificate", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fieldOfStudy.isEmpty()) {
            Toast.makeText(this, "Please enter field of study", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startDate.isEmpty()) {
            Toast.makeText(this, "Please enter start date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE).edit();
        editor.putString("education_school_name", schoolName);
        editor.putString("education_degree", degree);
        editor.putString("education_field_of_study", fieldOfStudy);
        editor.putString("education_start_date", startDate);
        editor.putString("education_end_date", endDate);
        editor.putString("education_gpa", gpa);
        editor.putString("education_location", location);
        editor.putString("education_description", description);
        editor.apply();

        Toast.makeText(this, "Education details saved successfully", Toast.LENGTH_SHORT).show();
    }
}