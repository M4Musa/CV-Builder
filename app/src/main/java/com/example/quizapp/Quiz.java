package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz extends AppCompatActivity {


   ImageButton btnprev;
   Button btnnext;
    RadioGroup rbgrp;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    TextView tvcount,tvquestion;

    String userName;
    int score,currentindex;
    int[] SelectedAnswers={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    String[] SelectedAnswersvalues;
    String[] historyQuestions= {
            "When did World War I begin?",
            "When did the United States declare independence?",
            "When did the French Revolution start?",
            "In which year did Christopher Columbus discover America?",
            "When was the Great Fire of London?",
            "When was the first Moon landing?",
            "When was the Declaration of the Rights of Man and of the Citizen adopted in France?",
            "In which year did the Berlin Wall fall?",
            "When did the Titanic sink?",
            "When did the Ottoman Empire officially dissolve?"
    };
    String[][] historyOptions = {
            {"1912", "1914", "1918", "1923"},  // Answer: 1914
            {"1770", "1776", "1783", "1801"},  // Answer: 1776
            {"1765", "1789", "1804", "1821"},  // Answer: 1789
            {"1453", "1492", "1517", "1607"},  // Answer: 1492
            {"1600", "1666", "1701", "1755"},  // Answer: 1666
            {"1957", "1965", "1969", "1972"},  // Answer: 1969
            {"1765", "1789", "1801", "1830"},  // Answer: 1789
            {"1975", "1985", "1989", "1991"},  // Answer: 1989
            {"1905", "1910", "1912", "1915"},  // Answer: 1912
            {"1908", "1918", "1922", "1925"}   // Answer: 1922
    };
    String[] historyAnswers = {
            "1914",
            "1776",
            "1789",
            "1492",
            "1666",
            "1969",
            "1789",
            "1989",
            "1912",
            "1922"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        init();
        btnprev.setOnClickListener((o) -> {
            if (currentindex == 0) {
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
            else {
                if(currentindex<10){
                    btnnext.setText("Next");
                }
                int  selectedid=rbgrp.getCheckedRadioButtonId();
                if(selectedid!=-1){
                    SelectedAnswers[currentindex]=selectedid;
                    RadioButton rbOption = findViewById(selectedid);
                    SelectedAnswersvalues[currentindex]=rbOption.getText().toString();
                }
                currentindex=currentindex-1;
                if(SelectedAnswers[currentindex]==-1){
                    rbgrp.clearCheck();
                }
                else{
                    rbgrp.check(SelectedAnswers[currentindex]);
                    }
                }
                tvquestion.setText(historyQuestions[currentindex]);
                tvcount.setText(currentindex+1+"/10");
                rb1.setText(historyOptions[currentindex][0]);
                rb2.setText(historyOptions[currentindex][1]);
                rb3.setText(historyOptions[currentindex][2]);
                rb4.setText(historyOptions[currentindex][3]);

        });

        btnnext.setOnClickListener((o)->{
            if(currentindex==8){
                btnnext.setText("Submit");
            }
            if(currentindex<9) {
                int selectedId = rbgrp.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    SelectedAnswers[currentindex]=selectedId;
                    RadioButton rbOption = findViewById(selectedId);
                    SelectedAnswersvalues[currentindex]=rbOption.getText().toString();
                }
                currentindex =currentindex+ 1;

                if(SelectedAnswers[currentindex]==-1){
                    rbgrp.clearCheck();
                }
                else{
                    rbgrp.check(SelectedAnswers[currentindex]);
                }
                tvquestion.setText(historyQuestions[currentindex]);
                tvcount.setText(currentindex + 1 + "/10");
                rb1.setText(historyOptions[currentindex][0]);
                rb2.setText(historyOptions[currentindex][1]);
                rb3.setText(historyOptions[currentindex][2]);
                rb4.setText(historyOptions[currentindex][3]);

            }
            else {
                btnnext.setText("Submit");
                for(int i=0;i<=9;i++){
                    if(historyAnswers[i]==SelectedAnswersvalues[i]){
                        score++;
                    }
                }
                Intent intent = new Intent(this, Result.class);
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("Score", score);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
         userName = getIntent().getStringExtra("USER_NAME");
        Toast.makeText(this, "Welcome " + userName, Toast.LENGTH_SHORT).show();
    }
    private void  init(){
        btnprev=findViewById(R.id.btnprev);
        btnnext=findViewById(R.id.btnNext);
        rbgrp=findViewById(R.id.radioGroup);
        tvquestion=findViewById(R.id.tvquestion);
        tvcount=findViewById(R.id.tvcount);
        rb1 = findViewById(R.id.rbopt1);
        rb2 = findViewById(R.id.rbopt2);
        rb3 = findViewById(R.id.rbopt3);
        rb4 = findViewById(R.id.rbopt4);
        SelectedAnswersvalues = new String[historyQuestions.length];
        currentindex=0;
        score=0;
        tvquestion.setText(historyQuestions[currentindex]);
        tvcount.setText(currentindex+1+"/10");
        rb1.setText(historyOptions[currentindex][0]);
        rb2.setText(historyOptions[currentindex][1]);
        rb3.setText(historyOptions[currentindex][2]);
        rb4.setText(historyOptions[currentindex][3]);
    }
}