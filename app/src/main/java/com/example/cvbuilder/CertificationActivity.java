package com.example.cvbuilder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CertificationActivity extends AppCompatActivity {

    private EditText etCertificationName;
    private EditText etIssuingOrganization;
    private EditText etIssueDate;
    private EditText etExpiryDate;
    private EditText etCredentialId;
    private EditText etCredentialUrl;
    private Button btnSave;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certification);

        // Initialize UI elements
        initUI();

        // Load existing data if available
        loadExistingData();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        etCertificationName = findViewById(R.id.etCertificationName);
        etIssuingOrganization = findViewById(R.id.etIssuingOrganization);
        etIssueDate = findViewById(R.id.etIssueDate);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCredentialId = findViewById(R.id.etCredentialId);
        etCredentialUrl = findViewById(R.id.etCredentialUrl);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadExistingData() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        etCertificationName.setText(prefs.getString("certification_name", ""));
        etIssuingOrganization.setText(prefs.getString("certification_organization", ""));
        etIssueDate.setText(prefs.getString("certification_issue_date", ""));
        etExpiryDate.setText(prefs.getString("certification_expiry_date", ""));
        etCredentialId.setText(prefs.getString("certification_credential_id", ""));
        etCredentialUrl.setText(prefs.getString("certification_credential_url", ""));
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
        String certificationName = etCertificationName.getText().toString().trim();
        String issuingOrganization = etIssuingOrganization.getText().toString().trim();
        String issueDate = etIssueDate.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();
        String credentialId = etCredentialId.getText().toString().trim();
        String credentialUrl = etCredentialUrl.getText().toString().trim();

        // Validate required fields
        if (certificationName.isEmpty()) {
            Toast.makeText(this, "Please enter certification name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (issuingOrganization.isEmpty()) {
            Toast.makeText(this, "Please enter issuing organization", Toast.LENGTH_SHORT).show();
            return;
        }
        if (issueDate.isEmpty()) {
            Toast.makeText(this, "Please enter issue date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE).edit();
        editor.putString("certification_name", certificationName);
        editor.putString("certification_organization", issuingOrganization);
        editor.putString("certification_issue_date", issueDate);
        editor.putString("certification_expiry_date", expiryDate);
        editor.putString("certification_credential_id", credentialId);
        editor.putString("certification_credential_url", credentialUrl);
        editor.apply();

        Toast.makeText(this, "Certification details saved successfully", Toast.LENGTH_SHORT).show();
    }
}