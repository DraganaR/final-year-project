package com.example.draga.creditscoreapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button questionsButton;
    private Button aboutButton;
    private Button tipsButton;
    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        System.out.println("Current user is: "+User.userId);

        questionsButton = findViewById(R.id.startCalculating);
        aboutButton = findViewById(R.id.about);
        tipsButton = findViewById(R.id.tips);
        helpButton = findViewById(R.id.help);

        questionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToInputActivity();
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAboutActivity();
            }
        });

        tipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToTipsActivity();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                moveToHelpActivity();
            }
        });
    }

    private void moveToHelpActivity() {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }

    private void moveToAboutActivity() {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    private void moveToInputActivity() {
        Intent inputIntent = new Intent(this, InputActivity.class);
        startActivity(inputIntent);
    }

    private void moveToTipsActivity() {
        Intent tipsIntent = new Intent(this, TipsActivity.class);
        startActivity(tipsIntent);
    }
}
