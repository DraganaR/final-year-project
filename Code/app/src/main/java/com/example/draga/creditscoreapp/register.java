package com.example.draga.creditscoreapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class register extends Activity {
    private Button registerButton;
    String firstName, lastName, email, password;

    private UserEntry ue = new UserEntry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerUser);

        // What should happen when the register button is clicked
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canAddNewUser()){
                    // Uncomment the lines below!!
                    UserModel u = new UserModel(0,firstName, lastName, email, password);
                    ue.addUser(u);

                    // Debug toast
                    // Toast.makeText(register.this,"WORKING: " + u.toString(),Toast.LENGTH_LONG).show();
                    User.userId = new UserEntry(register.this).getIdByEmail(email);
                    startActivity(new Intent(register.this,MenuActivity.class));
                } else {
                    Toast.makeText(register.this,"something went wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Check if the user has filled in all of the fields
     * @return true / false
     */
    private boolean canAddNewUser() {
        final EditText firstNameInput = (EditText) findViewById(R.id.firstName);
        final EditText lastNameInput = (EditText) findViewById(R.id.lastName);
        final EditText emailInput = (EditText) findViewById(R.id.email);
        final EditText passwordInput = (EditText) findViewById(R.id.password);

        firstName = firstNameInput.getText().toString();
        lastName = lastNameInput.getText().toString();
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        if(firstName.equals("") || firstName.equals(" ")
                || lastName.equals("") || lastName.equals(" ")
                || email.equals("") || email.equals(" ")
                || password.equals("") || password.equals(" ")) {
            return false;
        } else {
            return true;
        }

    }

}