package com.example.cvbuilder;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity implements View.OnClickListener {

    // Button declarations
    private Button btnProfilePic;
    private Button btnPersonalDetails;
    private Button btnSummary;
    private Button btnEducation;
    private Button btnExperience;
    private Button btnCertification;
    private Button btnReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init(){
        // Initialize all buttons
        btnProfilePic = findViewById(R.id.btnprofilepic);
        btnPersonalDetails = findViewById(R.id.btnpersonaldetails);
        btnSummary = findViewById(R.id.btnsummary);
        btnEducation = findViewById(R.id.btneducation);
        btnExperience = findViewById(R.id.btnexperience);
        btnCertification = findViewById(R.id.btncertification);
        btnReference = findViewById(R.id.btnreference);

        // Set OnClickListener for all buttons
        btnProfilePic.setOnClickListener(this);
        btnPersonalDetails.setOnClickListener(this);
        btnSummary.setOnClickListener(this);
        btnEducation.setOnClickListener(this);
        btnExperience.setOnClickListener(this);
        btnCertification.setOnClickListener(this);
        btnReference.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnprofilepic) {
            // Handle Profile Picture button click
            Toast.makeText(this, "Profile Picture clicked", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(Home.this, ProfilePictureActivity.class);
            // startActivity(intent);
        }
        else if (id == R.id.btnpersonaldetails) {
            // Handle Personal Details button click
            Toast.makeText(this, "Personal Details clicked", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(Home.this, PersonalDetailsActivity.class);
            // startActivity(intent);
        }
        else if (id == R.id.btnsummary) {
            // Handle Summary button click
            Toast.makeText(this, "Summary clicked", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(Home.this, SummaryActivity.class);
            // startActivity(intent);
        }
        else if (id == R.id.btneducation) {
            // Handle Education button click
            Toast.makeText(this, "Education clicked", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(Home.this, EducationActivity.class);
            // startActivity(intent);
        }
        else if (id == R.id.btnexperience) {
            // Handle Experience button click
            Toast.makeText(this, "Experience clicked", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(Home.this, ExperienceActivity.class);
            // startActivity(intent);
        }
        else if (id == R.id.btncertification) {
            // Handle Certification button click
            Toast.makeText(this, "Certification clicked", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(Home.this, CertificationActivity.class);
            // startActivity(intent);
        }
        else if (id == R.id.btnreference) {
            // Handle Reference button click
            Toast.makeText(this, "Reference clicked", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(Home.this, ReferenceActivity.class);
            // startActivity(intent);
        }
    }
}