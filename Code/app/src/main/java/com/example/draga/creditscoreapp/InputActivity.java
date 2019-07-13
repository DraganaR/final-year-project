package com.example.draga.creditscoreapp;

import android.content.Intent;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//First page of questions
public class InputActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AnswerEntry ae = new AnswerEntry(this);

    private String[] paymentHouseholdHistory = {"On time", "1-30 days late", "30-60 days late", "60+ days late"};
    private String[] amountOwedHousehold = {"0-400", "401-600", "601-900", "901+"};
    private CheckBox mortgage, creditCard, autoLoan, creditUnion, paydayLoan;
    private String[] paymentLoans = {"0-5,000", "5,001-10,000", "10,001-20,000", "20,001+"};
    private String[] creditPayments = {"On time", "1-30 days late", "31-60 days late", "61+ days late"};
    private String[] creditCardPayments = {"Above amount due", "Amount due", "Below amount due"};
    private String[] creditApplication = {"1-3 times", "4-6 times", "7-9 times", "10+ times"};
    private String[] salary = {"1,000-1,900", "1,901-2,600", "2,601+"};

    private Button calculateScore;

    private Spinner billsSpinner, amountOwedHouseholdSpinner, paymentSpinner, creditSpinner, creditCardSpinner, cardApplication, salarySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mortgage = findViewById(R.id.mortgage);
        creditCard = findViewById(R.id.creditCard);
        autoLoan = findViewById(R.id.carLoan);
        creditUnion = findViewById(R.id.creditUnion);
        paydayLoan = findViewById(R.id.paydayLoan);

        billsSpinner = newSpinner(R.id.billsSpinner, paymentHouseholdHistory);
        amountOwedHouseholdSpinner = newSpinner(R.id.amountOwedHousehold, amountOwedHousehold);
        paymentSpinner = newSpinner(R.id.paymentSpinner, paymentLoans);
        creditSpinner = newSpinner(R.id.creditSpinner, creditPayments); //how often you pay your loans
        creditCardSpinner = newSpinner(R.id.creditCardSpinner, creditCardPayments);
        cardApplication = newSpinner(R.id.creditApplication, creditApplication);
        salarySpinner = newSpinner(R.id.salary, salary);

        Log.d("[ANSWER]", ae.getAllAnswers().toString());

        if(ae.getAnswerByUserId(User.userId) != null) { // If they have submitted an answer
            displayAnswers();
        }

        calculateScore = findViewById(R.id.calculateScore);
        calculateScore.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                calculatePoints();
                saveAnswers();
                Intent resultIntent = new Intent(InputActivity.this, ResultActivity.class);
                startActivity(resultIntent);
            }
        });
    }

    private void displayAnswers() {
        AnswerModel answer = ae.getAnswerByUserId(User.userId);

        Log.d("[ANSWER]", answer.toString());

        billsSpinner.setSelection(Arrays.asList(paymentHouseholdHistory).indexOf(answer.getQ1()));
        amountOwedHouseholdSpinner.setSelection(Arrays.asList(amountOwedHousehold).indexOf(answer.getQ2()));

        List<String> q3 = Arrays.asList(answer.getQ3().split(" "));
        if(q3.contains("mortgage")) {
            mortgage.setChecked(true);
        }
        if(q3.contains("creditCard")) {
            creditCard.setChecked(true);
        }
        if(q3.contains("autoLoan")) {
            autoLoan.setChecked(true);
        }
        if(q3.contains("creditUnion")) {
            creditUnion.setChecked(true);
        }
        if(q3.contains("paydayLoan")) {
            paydayLoan.setChecked(true);
        }

        paymentSpinner.setSelection(Arrays.asList(paymentLoans).indexOf(answer.getQ4()));
        creditSpinner.setSelection(Arrays.asList(creditPayments).indexOf(answer.getQ5()));
        creditCardSpinner.setSelection(Arrays.asList(creditCardPayments).indexOf(answer.getQ6()));
        cardApplication.setSelection(Arrays.asList(creditApplication).indexOf(answer.getQ7()));
        salarySpinner.setSelection(Arrays.asList(salary).indexOf(answer.getQ8()));
    }

    private void saveAnswers(){
        AnswerModel answer = new AnswerModel();
        answer.setId(ae.getNextId());
        answer.setUserId(User.userId);
        answer.setQ1(billsSpinner.getSelectedItem().toString());
        answer.setQ2(amountOwedHouseholdSpinner.getSelectedItem().toString());

        String q3 = " ";
        if(mortgage.isChecked()) {
            q3 += "mortgage ";
        }
        if(creditCard.isChecked()) {
            q3 += "creditCard ";
        }
        if(autoLoan.isChecked()) {
            q3 += "autoLoan ";
        }
        if(creditUnion.isChecked()) {
            q3 += "creditUnion ";
        }
        if(paydayLoan.isChecked()) {
            q3 += "paydayLoan ";
        }

        answer.setQ3(q3); // e.g "mortgage creditUnion"
        answer.setQ4(paymentSpinner.getSelectedItem().toString());
        answer.setQ5(creditSpinner.getSelectedItem().toString());
        answer.setQ6(creditCardSpinner.getSelectedItem().toString());
        answer.setQ7(cardApplication.getSelectedItem().toString());
        answer.setQ8(salarySpinner.getSelectedItem().toString());

        if(ae.getAnswerByUserId(User.userId) == null){
            ae.addAnswer(answer);
            Toast.makeText(InputActivity.this, "Your answers have been saved!", Toast.LENGTH_LONG).show();
        } else {
            ae.updateAnswer(answer);
            Toast.makeText(InputActivity.this, "Your answers have been updated!", Toast.LENGTH_LONG).show();
        }

        System.out.println("Answers.. "+ae.getAllAnswers().toString());
    }

    private void calculatePoints() {
        if(billsSpinner.getSelectedItemPosition() == 0) {
            User.points += Points.paymentHouseholdHistory[0];
        }
        if(billsSpinner.getSelectedItemPosition() == 1) {
            User.points += Points.paymentHouseholdHistory[1];
        }
        if(billsSpinner.getSelectedItemPosition() == 2) {
            User.points += Points.paymentHouseholdHistory[2];
        }
        if(billsSpinner.getSelectedItemPosition() == 3) {
            User.points += Points.paymentHouseholdHistory[3];
        }
        if(amountOwedHouseholdSpinner.getSelectedItemPosition() == 0) {
            User.points += Points.amountOwedHousehold[0];
        }
        if(amountOwedHouseholdSpinner.getSelectedItemPosition() == 1) {
            User.points += Points.amountOwedHousehold[1];
        }
        if(amountOwedHouseholdSpinner.getSelectedItemPosition() == 2) {
            User.points += Points.amountOwedHousehold[2];
        }
        if(amountOwedHouseholdSpinner.getSelectedItemPosition() == 3) {
            User.points += Points.amountOwedHousehold[3];
        }
        if(paymentSpinner.getSelectedItemPosition() == 0) {
            User.points += Points.paymentLoans[0];
        }
        if(paymentSpinner.getSelectedItemPosition() == 1) {
            User.points += Points.paymentLoans[1];
        }
        if(paymentSpinner.getSelectedItemPosition() == 2) {
            User.points += Points.paymentLoans[2];
        }
        if(paymentSpinner.getSelectedItemPosition() == 3) {
            User.points += Points.paymentLoans[3];
        }
        if(creditSpinner.getSelectedItemPosition() == 0) {
            User.points += Points.creditPayments[0];
        }
        if(creditSpinner.getSelectedItemPosition() == 1) {
            User.points += Points.creditPayments[1];
        }
        if(creditSpinner.getSelectedItemPosition() == 2) {
            User.points += Points.creditPayments[2];
        }
        if(creditSpinner.getSelectedItemPosition() == 3) {
            User.points += Points.creditPayments[3];
        }
        if(creditCardSpinner.getSelectedItemPosition() == 0) {
            User.points += Points.creditCardPayments[0];
        }
        if(creditCardSpinner.getSelectedItemPosition() == 1) {
            User.points += Points.creditCardPayments[1];
        }
        if(creditCardSpinner.getSelectedItemPosition() == 2) {
            User.points += Points.creditCardPayments[2];
        }
        if(cardApplication.getSelectedItemPosition() == 0) {
            User.points += Points.creditApplication[0];
        }
        if(cardApplication.getSelectedItemPosition() == 1) {
            User.points += Points.creditApplication[1];
        }
        if(cardApplication.getSelectedItemPosition() == 2) {
            User.points += Points.creditApplication[2];
        }
        if(cardApplication.getSelectedItemPosition() == 3) {
            User.points += Points.creditApplication[3];
        }
        if(salarySpinner.getSelectedItemPosition() == 0) {
            User.points += Points.salary[0];
        }
        if(salarySpinner.getSelectedItemPosition() == 1) {
            User.points += Points.salary[1];
        }
        if(salarySpinner.getSelectedItemPosition() == 2) {
            User.points += Points.salary[2];
        }
        if(mortgage.isChecked()) {
            User.points += Points.mortgage;
        }
        if(creditCard.isChecked()) {
            User.points += Points.creditCard;
        }
        if(autoLoan.isChecked()) {
            User.points += Points.autoLoan;
        }
        if(creditUnion.isChecked()) {
            User.points += Points.autoLoan;
        }
        if(paydayLoan.isChecked()) {
            User.points += Points.paydayLoan;
        }
    }

    private Spinner newSpinner(Integer spinnerId, String[] items) {
        Spinner spinner = (Spinner) findViewById(spinnerId);

        ArrayAdapter aa = new ArrayAdapter(InputActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        return spinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),paymentHouseholdHistory[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}