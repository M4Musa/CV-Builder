package com.example.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Result extends AppCompatActivity {

    TextView tvName,tvScore;
    Button btnShare;

    ImageButton btnprev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        init();
        btnShare.setOnClickListener(view -> {
            String name = tvName.getText().toString();
            String score = tvScore.getText().toString();

            String shareMessage = "Quiz Khelo Results :\n\nPlayer: " + name + "\nScore: " + score;

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
        btnprev.setOnClickListener((o) -> {
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to start again?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            });

    }
    @Override
    protected void onStart() {
        super.onStart();
        tvName.setText(getIntent().getStringExtra("USER_NAME"));
        int score = getIntent().getIntExtra("Score", 0);
        tvScore.setText(score + "/10");
    }
    private void init(){
        tvName=findViewById(R.id.tvname);
        tvScore=findViewById(R.id.tvscore);
        btnShare=findViewById(R.id.btnShare);
        btnprev=findViewById(R.id.btnprev);

    }
}