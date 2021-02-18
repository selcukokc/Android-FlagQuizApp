package com.selcukokc.flagquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.transform.Result;

public class QuizActivity extends AppCompatActivity {
    private Button buttonA,buttonB,buttonC,buttonD,button6;
    private TextView txtScoreCounter,txtRecordCounter,txtQuestionCounter;
    private ImageView imageView,imgHeart1,imgHeart2,imgHeart3;
    private ArrayList<Flags> questionsList;
    private ArrayList<Flags> falseOptionsList;
    private Flags question;
    private Database db;
    private int questionCounter=0;
    private int trueCounter=0;
    private int falseCounter=3;
    private HashSet<Flags> mixOptionsList = new HashSet<>();
    private ArrayList<Flags> options = new ArrayList<>();
    private int record;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        buttonA=findViewById(R.id.buttonA);
        buttonB=findViewById(R.id.buttonB);
        buttonC=findViewById(R.id.buttonC);
        buttonD=findViewById(R.id.buttonD);
        button6=findViewById(R.id.button6);
        txtScoreCounter=findViewById(R.id.txtScoreCounter);
        txtRecordCounter=findViewById(R.id.txtRecordCounter);
        txtQuestionCounter=findViewById(R.id.txtQuestionCounter);
        imageView=findViewById(R.id.imageView);
        imgHeart1=findViewById(R.id.imgHeart1);
        imgHeart2=findViewById(R.id.imgHeart2);
        imgHeart3=findViewById(R.id.imgHeart3);



        db = new Database(this);
        questionsList = new FlagsDao().randomQuestions(db);
        setQuestion();

        sp=getSharedPreferences("recordSP",MODE_PRIVATE);
        editor=sp.edit();
        record=sp.getInt("record",0);
        txtRecordCounter.setText("Record: "+record);


        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionControl(buttonA);
                buttonClickableReset();
                button6.setVisibility(View.VISIBLE);
                button6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        counterControl();
                    }
                });

            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionControl(buttonB);
                buttonClickableReset();
                button6.setVisibility(View.VISIBLE);
                button6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        counterControl();
                    }
                });

            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionControl(buttonC);
                buttonClickableReset();
                button6.setVisibility(View.VISIBLE);
                button6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        counterControl();
                    }
                });
            }
        });

        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionControl(buttonD);
                buttonClickableReset();
                button6.setVisibility(View.VISIBLE);
                button6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        counterControl();
                    }
                });
            }
        });







    }
    public void setQuestion(){
        buttonA.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        buttonB.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        buttonC.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        buttonD.setBackgroundColor(getResources().getColor(R.color.buttonColor));

        buttonA.setClickable(true);
        buttonB.setClickable(true);
        buttonC.setClickable(true);
        buttonD.setClickable(true);

        button6.setVisibility(View.INVISIBLE);
        txtQuestionCounter.setText((questionCounter+1)+"/196");
        question = questionsList.get(questionCounter);
        falseOptionsList = new FlagsDao().falseOptions(db,question.getFlag_id());
        imageView.setImageResource(getResources().getIdentifier(question.getFlag_img(),"drawable",getPackageName()));
        mixOptionsList.clear();
        mixOptionsList.add(question);
        mixOptionsList.add(falseOptionsList.get(0));
        mixOptionsList.add(falseOptionsList.get(1));
        mixOptionsList.add(falseOptionsList.get(2));

        options.clear();

        for(Flags f : mixOptionsList){
            options.add(f);
        }

        buttonA.setText(options.get(0).getFlag_name());
        buttonB.setText(options.get(1).getFlag_name());
        buttonC.setText(options.get(2).getFlag_name());
        buttonD.setText(options.get(3).getFlag_name());


    }


    public void questionControl(Button button){
        String buttonText = button.getText().toString();
        String trueAnswer = question.getFlag_name();
        if(buttonText.equals(trueAnswer)){
            trueCounter++;
            txtScoreCounter.setText("Score: "+trueCounter);
            button.setBackgroundColor(getResources().getColor(R.color.trueColor));

            button6.setVisibility(View.VISIBLE);
        }
        else{
            falseCounter--;
            button.setBackgroundColor(getResources().getColor(R.color.falseColor));

            String a = buttonA.getText().toString();
            String b = buttonB.getText().toString();
            String c = buttonC.getText().toString();
            String d = buttonD.getText().toString();
            //if wrong option will select, program will show the true answer
            if(a.equals(trueAnswer)){
                buttonA.setBackgroundColor(getResources().getColor(R.color.trueColor));
            }
            else if(b.equals(trueAnswer)){
                buttonB.setBackgroundColor(getResources().getColor(R.color.trueColor));
            }
            else if(c.equals(trueAnswer)){
                buttonC.setBackgroundColor(getResources().getColor(R.color.trueColor));
            }
            else if(d.equals(trueAnswer)){
                buttonD.setBackgroundColor(getResources().getColor(R.color.trueColor));
            }


            if(falseCounter==2){
                imgHeart3.setVisibility(View.INVISIBLE);
            }

            else if(falseCounter==1){
                imgHeart2.setVisibility(View.INVISIBLE);
            }

            else if(falseCounter==0) {
                imgHeart1.setVisibility((View.INVISIBLE));
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("trueCounter",trueCounter);
                startActivity(intent);
                finish();

            }

        }

    }

    public void counterControl(){
        ++questionCounter;
        if(questionCounter!=196){
            setQuestion();
        }
        else{
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("trueCounter",trueCounter);
            startActivity(intent);
            finish();
        }

    }

    public void buttonClickableReset(){
        buttonA.setClickable(false);
        buttonB.setClickable(false);
        buttonC.setClickable(false);
        buttonD.setClickable(false);
    }

}