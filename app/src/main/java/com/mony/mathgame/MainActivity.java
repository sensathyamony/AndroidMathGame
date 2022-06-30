package com.mony.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button addBtn, subBtn, multiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        multiBtn = findViewById(R.id.multiBtn);

        addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameScreen.class);
            intent.putExtra("gameMode", '+');
            startActivity(intent);
            finish();
        });

        subBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameScreen.class);
            intent.putExtra("gameMode", '-');
            startActivity(intent);
            finish();
        });

        multiBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameScreen.class);
            intent.putExtra("gameMode", '*');
            startActivity(intent);
            finish();
        });

    }
}