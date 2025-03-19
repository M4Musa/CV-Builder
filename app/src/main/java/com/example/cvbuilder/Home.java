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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Home extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private Button btnSelectImage;
    private Button btnPersonalDetails;
    private Button btnSummary;
    private Button btnEducation;
    private Button btnExperience;
    private Button btnCertification;
    private Button btnReference;
    private Button btnPreview;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Initialize UI elements
        initUI();

        // Set up image picker launcher
        setupImagePicker();

        // Load existing profile picture if available
        loadProfilePicture();

        // Set up button click listeners
        setupListeners();
    }

    private void initUI() {
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnPersonalDetails = findViewById(R.id.btnPersonalDetails);
        btnSummary = findViewById(R.id.btnSummary);
        btnEducation = findViewById(R.id.btnEducation);
        btnExperience = findViewById(R.id.btnExperience);
        btnCertification = findViewById(R.id.btnCertification);
        btnReference = findViewById(R.id.btnReference);
        btnPreview = findViewById(R.id.btnPreview);
    }

    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        try {
                            // Save the image to internal storage
                            String savedImagePath = saveImageToInternalStorage(selectedImageUri);
                            
                            // Save the path to SharedPreferences
                            SharedPreferences.Editor editor = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE).edit();
                            editor.putString("profile_picture_path", savedImagePath);
                            editor.apply();

                            // Display the image
                            Bitmap bitmap = BitmapFactory.decodeFile(savedImagePath);
                            // Convert to circular bitmap
                            bitmap = getCircularBitmap(bitmap);
                            ivProfilePicture.setImageBitmap(bitmap);
                            
                            Toast.makeText(this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error saving profile picture", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        );
    }

    private String saveImageToInternalStorage(Uri imageUri) throws IOException {
        // Create a file in the app's private directory
        File directory = getFilesDir();
        File imageFile = new File(directory, "profile_picture.jpg");

        // Copy the image data to the file
        try (InputStream in = getContentResolver().openInputStream(imageUri);
             FileOutputStream out = new FileOutputStream(imageFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }

        return imageFile.getAbsolutePath();
    }

    private void loadProfilePicture() {
        SharedPreferences prefs = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);
        String profilePicturePath = prefs.getString("profile_picture_path", "");
        
        if (!profilePicturePath.isEmpty()) {
            File file = new File(profilePicturePath);
            if (file.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                    // Convert to circular bitmap
                    bitmap = getCircularBitmap(bitmap);
                    ivProfilePicture.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private void setupListeners() {
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imagePickerLauncher.launch(intent);
            }
        });

        btnPersonalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, PersonalDetails.class));
            }
        });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, SummaryActivity.class));
            }
        });

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, EducationActivity.class));
            }
        });

        btnExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ExperienceActivity.class));
            }
        });

        btnCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, CertificationActivity.class));
            }
        });

        btnReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ReferenceActivity.class));
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, PreviewActivity.class));
            }
        });
    }
}