package com.mony.mathgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    TextView scoreTxt, timerTxt, questionText, lifeTxt;
    Button subButton, nextButton;
    EditText editText;

    CountDownTimer timer;
    Random random = new Random();

    int number1, number2, userScore = 0, totalAnswer, lifeCount = 3;

    private static final long START_TIME = 10000;
    long time_left = START_TIME;
    boolean isRunning = false;

    char sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        lifeTxt = findViewById(R.id.lifeText);
        questionText = findViewById(R.id.questionText);
        scoreTxt = findViewById(R.id.highScoreText);
        timerTxt = findViewById(R.id.timerText);
        subButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        editText = findViewById(R.id.ansText);


        sign = getIntent().getCharExtra("gameMode", '+');
        getQuestion(sign);
        lifeTxt.setText(String.valueOf(lifeCount));

        subButton.setOnClickListener(view -> {
            if (editText.getText().toString().equals("")){
                Toast.makeText(this, "Please input your Answer", Toast.LENGTH_LONG).show();
            }else {
                int userAns = Integer.valueOf(editText.getText().toString());
                if (userAns == totalAnswer) {
                    userScore = userScore + 10;
                    questionText.setText("Hooray you had answer correct");
                } else {
                    userScore = userScore - 10;
                    lifeCount--;
                    questionText.setText("Sorry the answer is " + totalAnswer);
                }
                lifeTxt.setText(String.valueOf(lifeCount));
                scoreTxt.setText(String.valueOf(userScore));
                editText.setEnabled(false);
                subButton.setEnabled(false);
                pauseTime();
                if (lifeCount == 0) {
                    questionText.setText("Game Over!");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("GAME OVER")
                            .setMessage("Do you want to restart?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(GameScreen.this, GameScreen.class);
                                    intent.putExtra("gameMode", sign);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(GameScreen.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).show();
                    dialog.create();
                }
            }
        });

        nextButton.setOnClickListener(view -> {
            subButton.setEnabled(true);
            editText.setEnabled(true);
            getQuestion(sign);
            restartTime();
        });

    }

    public void getQuestion(char sign){
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);

        switch (sign){
            case '-':
                totalAnswer = number1 - number2;
                questionText.setText(number1 + " - " + number2);
                break;
            case '*':
                totalAnswer = number1 * number2;
                questionText.setText(number1 + " * " + number2);
                break;
            default:
                totalAnswer = number1 + number2;
                questionText.setText(number1 + " + " + number2);
                break;
        }
        editText.setText("");

        setTimer();

    }

    public void setTimer(){
        timer = new CountDownTimer(START_TIME, 1000) {
            @Override
            public void onTick(long l) {
                time_left = l;
                updateTime();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                pauseTime();
                restartTime();
                updateTime();
                userScore = userScore - 10;
                scoreTxt.setText(String.valueOf(userScore));
                lifeCount--;
                lifeTxt.setText(String.valueOf(lifeCount));
                questionText.setText("TIME OUT!");
                subButton.setEnabled(false);
                editText.setEnabled(false);
            }
        }.start();
    }

    public void updateTime(){
        int second = (int) (time_left / 1000) % 60;
        String timerFormat = String.format(Locale.getDefault(), "%02d", second);
        timerTxt.setText(timerFormat);
    }

    public void pauseTime(){
        timer.cancel();
        isRunning = false;
    }

    public void restartTime(){
        time_left = START_TIME;
        updateTime();
    }
}