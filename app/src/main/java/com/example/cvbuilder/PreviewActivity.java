package com.example.cvbuilder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PreviewActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvFullName, tvEmail, tvPhone, tvAddress;
    private TextView tvSummary, tvSkills, tvLanguages, tvInterests;
    private TextView tvEducation, tvExperience, tvCertifications, tvReferences;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_preview);

        // Initialize UI elements
        initUI();

        // Load all CV data
        loadCVData();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        // Initialize profile section
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);

        // Initialize summary section
        tvSummary = findViewById(R.id.tvSummary);
        tvSkills = findViewById(R.id.tvSkills);
        tvLanguages = findViewById(R.id.tvLanguages);
        tvInterests = findViewById(R.id.tvInterests);

        // Initialize other sections
        tvEducation = findViewById(R.id.tvEducation);
        tvExperience = findViewById(R.id.tvExperience);
        tvCertifications = findViewById(R.id.tvCertifications);
        tvReferences = findViewById(R.id.tvReferences);

        // Initialize buttons
        btnShare = findViewById(R.id.btnShare);

    }

    private void loadCVData() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        // Load personal details
        tvFullName.setText(prefs.getString("personal_fullname", ""));
        tvEmail.setText(prefs.getString("personal_email", ""));
        tvPhone.setText(prefs.getString("personal_phone", ""));
        tvAddress.setText(prefs.getString("personal_address", ""));

        // Load profile picture
        String profilePicturePath = prefs.getString("profile_picture_path", "");
        if (!profilePicturePath.isEmpty()) {
            try {
                File file = new File(profilePicturePath);
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                // Convert to circular bitmap
                bitmap = getCircularBitmap(bitmap);
                ivProfilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Load summary section
        tvSummary.setText(prefs.getString("summary_text", ""));
        tvSkills.setText(prefs.getString("summary_skills", ""));
        tvLanguages.setText(prefs.getString("summary_languages", ""));
        tvInterests.setText(prefs.getString("summary_interests", ""));

        // Load education
        StringBuilder educationText = new StringBuilder();
        educationText.append(prefs.getString("education_school_name", "")).append("\n");
        educationText.append(prefs.getString("education_degree", "")).append(" in ");
        educationText.append(prefs.getString("education_field_of_study", "")).append("\n");
        educationText.append(prefs.getString("education_start_date", "")).append(" - ");
        educationText.append(prefs.getString("education_end_date", "")).append("\n");
        educationText.append("GPA: ").append(prefs.getString("education_gpa", "")).append("\n");
        educationText.append(prefs.getString("education_location", "")).append("\n");
        educationText.append(prefs.getString("education_description", ""));
        tvEducation.setText(educationText.toString());

        // Load work experience
        StringBuilder experienceText = new StringBuilder();
        experienceText.append(prefs.getString("experience_company_name", "")).append("\n");
        experienceText.append(prefs.getString("experience_position", "")).append("\n");
        experienceText.append(prefs.getString("experience_location", "")).append("\n");
        experienceText.append(prefs.getString("experience_start_date", "")).append(" - ");
        experienceText.append(prefs.getString("experience_end_date", "")).append("\n");
        experienceText.append(prefs.getString("experience_employment_type", "")).append("\n\n");
        experienceText.append("Description:\n").append(prefs.getString("experience_description", "")).append("\n\n");
        experienceText.append("Achievements:\n").append(prefs.getString("experience_achievements", ""));
        tvExperience.setText(experienceText.toString());

        // Load certifications
        StringBuilder certificationText = new StringBuilder();
        certificationText.append(prefs.getString("certification_name", "")).append("\n");
        certificationText.append(prefs.getString("certification_organization", "")).append("\n");
        certificationText.append("Issued: ").append(prefs.getString("certification_issue_date", "")).append("\n");
        String expiryDate = prefs.getString("certification_expiry_date", "");
        if (!expiryDate.isEmpty()) {
            certificationText.append("Expires: ").append(expiryDate).append("\n");
        }
        String credentialId = prefs.getString("certification_credential_id", "");
        if (!credentialId.isEmpty()) {
            certificationText.append("Credential ID: ").append(credentialId).append("\n");
        }
        String credentialUrl = prefs.getString("certification_credential_url", "");
        if (!credentialUrl.isEmpty()) {
            certificationText.append("URL: ").append(credentialUrl);
        }
        tvCertifications.setText(certificationText.toString());

        // Load references
        StringBuilder referenceText = new StringBuilder();
        referenceText.append(prefs.getString("reference_name", "")).append("\n");
        referenceText.append(prefs.getString("reference_position", "")).append(" at ");
        referenceText.append(prefs.getString("reference_company", "")).append("\n");
        referenceText.append("Email: ").append(prefs.getString("reference_email", "")).append("\n");
        referenceText.append("Phone: ").append(prefs.getString("reference_phone", "")).append("\n");
        referenceText.append("Relationship: ").append(prefs.getString("reference_relationship", "")).append("\n");
        referenceText.append(prefs.getString("reference_notes", ""));
        tvReferences.setText(referenceText.toString());
    }

    private void setupListeners() {
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCV();
            }
        });

    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void shareCV() {
        // Create a text file with CV content
        String cvContent = createCVContent();
        File cvFile = saveCVToFile(cvContent);

        if (cvFile != null) {
            // Share the file
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            Uri fileUri = FileProvider.getUriForFile(this,
                    "com.example.cvbuilder.fileprovider",
                    cvFile);
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CV");
            startActivity(Intent.createChooser(shareIntent, "Share CV"));
        }
    }

    private void downloadCV() {
        // Create a text file with CV content
        String cvContent = createCVContent();
        File cvFile = saveCVToFile(cvContent);

        if (cvFile != null) {
            // Move the file to Downloads directory
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            File destinationFile = new File(downloadsDir, "CV_" + timestamp + ".txt");

            try {
                FileInputStream in = new FileInputStream(cvFile);
                FileOutputStream out = new FileOutputStream(destinationFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
                Toast.makeText(this, "CV downloaded to Downloads folder", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error downloading CV", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String createCVContent() {
        StringBuilder content = new StringBuilder();
        content.append("CURRICULUM VITAE\n\n");

        // Personal Details
        content.append("PERSONAL DETAILS\n");
        content.append("Name: ").append(tvFullName.getText()).append("\n");
        content.append("Email: ").append(tvEmail.getText()).append("\n");
        content.append("Phone: ").append(tvPhone.getText()).append("\n");
        content.append("Address: ").append(tvAddress.getText()).append("\n\n");

        // Professional Summary
        content.append("PROFESSIONAL SUMMARY\n");
        content.append(tvSummary.getText()).append("\n\n");

        // Skills
        content.append("KEY SKILLS\n");
        content.append(tvSkills.getText()).append("\n\n");

        // Education
        content.append("EDUCATION\n");
        content.append(tvEducation.getText()).append("\n\n");

        // Work Experience
        content.append("WORK EXPERIENCE\n");
        content.append(tvExperience.getText()).append("\n\n");

        // Certifications
        content.append("CERTIFICATIONS\n");
        content.append(tvCertifications.getText()).append("\n\n");

        // References
        content.append("REFERENCES\n");
        content.append(tvReferences.getText()).append("\n\n");

        // Additional Information
        content.append("ADDITIONAL INFORMATION\n");
        content.append("Languages: ").append(tvLanguages.getText()).append("\n");
        content.append("Professional Interests: ").append(tvInterests.getText());

        return content.toString();
    }

    private File saveCVToFile(String content) {
        try {
            File file = new File(getCacheDir(), "cv.txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating CV file", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
} 