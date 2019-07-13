package com.example.draga.creditscoreapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String result = "ERROR";
        String paragraph = "-1";
        if(User.points >= 0 && User.points <170) {
            result = "BAD";
            paragraph = "Your score was categorised as 'BAD'." +
                    "To better your score - make sure that you are " +
                    "paying back your bills on time. This is the biggest" +
                    "contributor resulting in a bad score. For your next" +
                    "bills, make sure to set reminders when to pay.";
        } else if(User.points >= 170 && User.points < 320) {
            result = "POOR";
            paragraph = "Your score was categorised as 'POOR'." +
                    "To better your score - You're just not quite there yet. Try reducing " +
                    "the amount of credit cards you have - and if that's not many, then" +
                    "make sure you pay your bills on time and the amount being asked for.";
        } else if(User.points >= 320 && User.points < 460) {
            result = "FAIR";
            paragraph = "Your score was categorised as 'FAIR'. " +
                    "Nearly there! To better your score - make sure that you are " +
                    "reducing the amount of debt owed on each card. Overpaying your bills" +
                    "will also cause your score to rise so always try to pay more often or" +
                    "earlier on the likes of your phone bill and electricity.";
        } else if(User.points >= 460 && User.points < 600) {
            result = "GOOD";
            paragraph = "Your score was categorised as 'GOOD'." +
                    "Most lenders would be happy with a score in this category, however, if you " +
                    "do want to make it better, you could take money out for the week so there's " +
                    "no personal spending shown on a card.";
        } else if(User.points >= 600) {
            result = "EXCELLENT";
            paragraph = "Your score was categorised as 'EXCELLENT'." +
                    "There is nothing else necessary for improving this score.";
        }

        ((TextView)findViewById(R.id.resultTextView)).setText(result);
        ((TextView)findViewById(R.id.paragraphView)).setText(paragraph);
        CustomGauge g = findViewById(R.id.myGauge);
        g.setStartAngle(135);
        g.setSweepAngle(270);
        g.setStartValue(0);
        g.setEndValue(100);
        g.setPointStartColor(getResources().getColor(R.color.fair));
        g.setPointEndColor(getResources().getColor(R.color.colorAccent));
        g.setValue(User.points);

        TextView points = findViewById(R.id.resultView);
        points.setText("" + User.points);
        User.points = 0;
    }
}
