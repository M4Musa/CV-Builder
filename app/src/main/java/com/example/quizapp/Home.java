package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class Home extends AppCompatActivity {

    Animation slideanim;
    EditText etname;
    Button btnStart;

    String Name ;


    ImageView ivlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        init();
        btnStart.setOnClickListener((o) -> {
            Name = etname.getText().toString();
            if (Name.trim().isEmpty()) { // Trim to avoid accidental spaces being considered valid input
                Toast.makeText(this, "Please enter your name to start", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, Quiz.class);
                intent.putExtra("USER_NAME", Name);
                startActivity(intent);
            }
        });

    }
    private void init(){
        ivlogo=findViewById(R.id.ivlogohome);
        etname=findViewById(R.id.etname);
        btnStart=findViewById(R.id.btnstart);
        slideanim= AnimationUtils.loadAnimation(this,R.anim.slide);
        ivlogo.startAnimation(slideanim);


    }

}