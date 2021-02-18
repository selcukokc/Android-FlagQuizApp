package com.selcukokc.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView txtScore,txtGameOver,txtNewRecord;
    private ImageView imgCup;
    private Button button2;
    private int trueCounter;
    private int recordCounter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtScore=findViewById(R.id.txtScore);
        txtGameOver=findViewById(R.id.txtGameOver);
        txtNewRecord=findViewById(R.id.txtNewRecord);
        imgCup=findViewById(R.id.imgCup);
        button2=findViewById(R.id.button2);

        sp=getSharedPreferences("recordSP",MODE_PRIVATE);
        editor=sp.edit();

        trueCounter=getIntent().getIntExtra("trueCounter",0);
        txtScore.setText("Your Score: "+trueCounter);
        recordCounter=sp.getInt("record",0);

        if(trueCounter>recordCounter){
            txtNewRecord.setVisibility(View.VISIBLE);
            imgCup.setVisibility(View.VISIBLE);

            editor.putInt("record",trueCounter);
            editor.commit();
        }

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,QuizActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}