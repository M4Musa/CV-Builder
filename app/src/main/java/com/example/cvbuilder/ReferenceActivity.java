package com.example.cvbuilder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ReferenceActivity extends AppCompatActivity {

    private EditText etReferenceName;
    private EditText etPosition;
    private EditText etCompany;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etRelationship;
    private EditText etNotes;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reference);

        // Initialize UI elements
        initUI();

        // Load existing data if available
        loadExistingData();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        etReferenceName = findViewById(R.id.etReferenceName);
        etPosition = findViewById(R.id.etPosition);
        etCompany = findViewById(R.id.etCompany);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etRelationship = findViewById(R.id.etRelationship);
        etNotes = findViewById(R.id.etNotes);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadExistingData() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        etReferenceName.setText(prefs.getString("reference_name", ""));
        etPosition.setText(prefs.getString("reference_position", ""));
        etCompany.setText(prefs.getString("reference_company", ""));
        etEmail.setText(prefs.getString("reference_email", ""));
        etPhone.setText(prefs.getString("reference_phone", ""));
        etRelationship.setText(prefs.getString("reference_relationship", ""));
        etNotes.setText(prefs.getString("reference_notes", ""));
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
        String referenceName = etReferenceName.getText().toString().trim();
        String position = etPosition.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String relationship = etRelationship.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        // Validate required fields
        if (referenceName.isEmpty()) {
            Toast.makeText(this, "Please enter reference name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (position.isEmpty()) {
            Toast.makeText(this, "Please enter position/title", Toast.LENGTH_SHORT).show();
            return;
        }
        if (company.isEmpty()) {
            Toast.makeText(this, "Please enter company/organization", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (relationship.isEmpty()) {
            Toast.makeText(this, "Please enter relationship", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE).edit();
        editor.putString("reference_name", referenceName);
        editor.putString("reference_position", position);
        editor.putString("reference_company", company);
        editor.putString("reference_email", email);
        editor.putString("reference_phone", phone);
        editor.putString("reference_relationship", relationship);
        editor.putString("reference_notes", notes);
        editor.apply();

        Toast.makeText(this, "Reference details saved successfully", Toast.LENGTH_SHORT).show();
    }
}