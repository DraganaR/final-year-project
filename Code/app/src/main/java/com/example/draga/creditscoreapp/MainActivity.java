package com.example.draga.creditscoreapp;

import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//Login and register
public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;

    private UserEntry userEntry = new UserEntry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.LogIn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ((EditText) findViewById(R.id.username)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();

                System.out.println(email + "..." + password);

                boolean validEmail = (userEntry.hasEmail(email));
                boolean correctPassword = (userEntry.getPasswordByEmail(email).equalsIgnoreCase(password));

                System.out.println(validEmail + "..." + correctPassword);

                if(validEmail && correctPassword) {
                    User.userId = userEntry.getIdByEmail(email);
                    moveToMenuActivity();
                } else {
                    Toast.makeText(MainActivity.this, "Could not login...", Toast.LENGTH_LONG).show();
                }
            }
        });
        registerButton = (Button) findViewById(R.id.registerLogin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, register.class));
            }
        });
    }

    private void moveToMenuActivity() {
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }
}
