package com.example.cvbuilder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    private EditText etSummary;
    private EditText etSkills;
    private EditText etLanguages;
    private EditText etInterests;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);

        // Initialize UI elements
        initUI();

        // Load existing data if available
        loadExistingData();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        etSummary = findViewById(R.id.etSummary);
        etSkills = findViewById(R.id.etSkills);
        etLanguages = findViewById(R.id.etLanguages);
        etInterests = findViewById(R.id.etInterests);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadExistingData() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        etSummary.setText(prefs.getString("summary_text", ""));
        etSkills.setText(prefs.getString("summary_skills", ""));
        etLanguages.setText(prefs.getString("summary_languages", ""));
        etInterests.setText(prefs.getString("summary_interests", ""));
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
        String summary = etSummary.getText().toString().trim();
        String skills = etSkills.getText().toString().trim();
        String languages = etLanguages.getText().toString().trim();
        String interests = etInterests.getText().toString().trim();

        // Validate required fields
        if (summary.isEmpty()) {
            Toast.makeText(this, "Please enter your professional summary", Toast.LENGTH_SHORT).show();
            return;
        }
        if (skills.isEmpty()) {
            Toast.makeText(this, "Please enter your key skills", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE).edit();
        editor.putString("summary_text", summary);
        editor.putString("summary_skills", skills);
        editor.putString("summary_languages", languages);
        editor.putString("summary_interests", interests);
        editor.apply();

        Toast.makeText(this, "Professional summary saved successfully", Toast.LENGTH_SHORT).show();
    }
}