package com.example.cvbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView ivlogo;
    Animation logoanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        ivlogo=findViewById(R.id.ivlogo);
        logoanim= AnimationUtils.loadAnimation(this,R.anim.bounce);
        ivlogo.startAnimation(logoanim);
        new Handler()
                .postDelayed(()->{
                    startActivity(new Intent(MainActivity.this, Home.class));
                    finish();
                }, 5000);
    }



}