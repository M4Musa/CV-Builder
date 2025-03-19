package com.example.cvbuilder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonalDetails extends AppCompatActivity {

     EditText etFullName;
     EditText etEmail;
     EditText etPhone;
     EditText etAddress;
     EditText etLinkedIn;
     Button btnSave;
     Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_details);

        // Initialize UI elements
        initUI();

        // Load existing data if available
        loadExistingData();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etLinkedIn = findViewById(R.id.etLinkedIn);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadExistingData() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        etFullName.setText(prefs.getString("personal_fullname", ""));
        etEmail.setText(prefs.getString("personal_email", ""));
        etPhone.setText(prefs.getString("personal_phone", ""));
        etAddress.setText(prefs.getString("personal_address", ""));
        etLinkedIn.setText(prefs.getString("personal_linkedin", ""));
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
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String linkedin = etLinkedIn.getText().toString().trim();

        // Validate data (simple validation example)
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE).edit();
        editor.putString("personal_fullname", fullName);
        editor.putString("personal_email", email);
        editor.putString("personal_phone", phone);
        editor.putString("personal_address", address);
        editor.putString("personal_linkedin", linkedin);
        editor.apply();

        Toast.makeText(this, "Personal details saved successfully", Toast.LENGTH_SHORT).show();
    }
}