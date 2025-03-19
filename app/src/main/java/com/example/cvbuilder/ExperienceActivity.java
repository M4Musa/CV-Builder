package com.example.cvbuilder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ExperienceActivity extends AppCompatActivity {

    private EditText etCompanyName;
    private EditText etPosition;
    private EditText etLocation;
    private EditText etStartDate;
    private EditText etEndDate;
    private EditText etEmploymentType;
    private EditText etDescription;
    private EditText etAchievements;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_experience);

        // Initialize UI elements
        initUI();

        // Load existing data if available
        loadExistingData();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        etCompanyName = findViewById(R.id.etCompanyName);
        etPosition = findViewById(R.id.etPosition);
        etLocation = findViewById(R.id.etLocation);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etEmploymentType = findViewById(R.id.etEmploymentType);
        etDescription = findViewById(R.id.etDescription);
        etAchievements = findViewById(R.id.etAchievements);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadExistingData() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        etCompanyName.setText(prefs.getString("experience_company_name", ""));
        etPosition.setText(prefs.getString("experience_position", ""));
        etLocation.setText(prefs.getString("experience_location", ""));
        etStartDate.setText(prefs.getString("experience_start_date", ""));
        etEndDate.setText(prefs.getString("experience_end_date", ""));
        etEmploymentType.setText(prefs.getString("experience_employment_type", ""));
        etDescription.setText(prefs.getString("experience_description", ""));
        etAchievements.setText(prefs.getString("experience_achievements", ""));
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
        String companyName = etCompanyName.getText().toString().trim();
        String position = etPosition.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String startDate = etStartDate.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();
        String employmentType = etEmploymentType.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String achievements = etAchievements.getText().toString().trim();

        // Validate required fields
        if (companyName.isEmpty()) {
            Toast.makeText(this, "Please enter company name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (position.isEmpty()) {
            Toast.makeText(this, "Please enter position/title", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return;
        }
        if (startDate.isEmpty()) {
            Toast.makeText(this, "Please enter start date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            Toast.makeText(this, "Please enter job description", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE).edit();
        editor.putString("experience_company_name", companyName);
        editor.putString("experience_position", position);
        editor.putString("experience_location", location);
        editor.putString("experience_start_date", startDate);
        editor.putString("experience_end_date", endDate);
        editor.putString("experience_employment_type", employmentType);
        editor.putString("experience_description", description);
        editor.putString("experience_achievements", achievements);
        editor.apply();

        Toast.makeText(this, "Work experience saved successfully", Toast.LENGTH_SHORT).show();
    }
}