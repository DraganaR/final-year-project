package com.example.draga.creditscoreapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class AnswerEntry {
    private DatabaseHelper dbh;

    public AnswerEntry (Context context){
     dbh = new DatabaseHelper(context);
    }

    public List<AnswerModel> getAllAnswers(){
        {
            List<AnswerModel> answers = new LinkedList<AnswerModel>();

            String query = "SELECT * FROM answer"; // Query to execute

            SQLiteDatabase db = dbh.getWritableDatabase(); // Get a writeable database instance

            Cursor c = db.rawQuery(query, null); // Set up the cursor

            // Add all of the users to the list
            AnswerModel answer = null;
            if(c.moveToFirst()) {
                do {
                    answer = new AnswerModel();
                    answer.setId(Integer.parseInt(c.getString(0)));
                    answer.setUserId(Integer.parseInt(c.getString(1)));
                    answer.setQ1(c.getString(2));
                    answer.setQ2(c.getString(3));
                    answer.setQ3(c.getString(4));
                    answer.setQ4(c.getString(5));
                    answer.setQ5(c.getString(6));
                    answer.setQ6(c.getString(7));
                    answer.setQ7(c.getString(8));
                    answer.setQ8(c.getString(9));

                    answers.add(answer);
                } while(c.moveToNext());
            }

            return answers;
        }
    }

    public AnswerModel getAnswerById(int id){
        for(AnswerModel a:getAllAnswers()){
            if (a.getId()== id){
                return a;
            } else {
                return null;
            }
        }
        return null;
    }

    public AnswerModel getAnswerByUserId(int userId){
        for(AnswerModel a:getAllAnswers()){
            if (a.getUserId()== userId){
                return a;
            } else {
                return null;
            }
        }
        return null;
    }

    public void addAnswer(AnswerModel answer){
        Log.d("[DEBUG]", answer.toString());

        // Get a writeable DB instance
        SQLiteDatabase db = dbh.getWritableDatabase();

        // Set up an object to hold the values of the user to be inserted
        ContentValues values = new ContentValues();
        values.put("id", answer.getId());
        values.put("userId", answer.getUserId());
        values.put("q1", answer.getQ1());
        values.put("q2", answer.getQ2());
        values.put("q3", answer.getQ3());
        values.put("q4", answer.getQ4());
        values.put("q5", answer.getQ5());
        values.put("q6", answer.getQ6());
        values.put("q7", answer.getQ7());
        values.put("q8", answer.getQ8());

        // Insert the user into the DB
        db.insert("answer", null, values);

        // Close the DB instance
        db.close();
    }



    public int getNextId() {
        int max = 0;
        for(AnswerModel a: getAllAnswers()) {
            if(a.getId() > max) {
                max = a.getId();
            }
        }

        return max + 1;
    }

    public void updateAnswer(AnswerModel answer){
        SQLiteDatabase db = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id" , answer.getId());
        values.put("userId", answer.getUserId());
        values.put("q1", answer.getQ1());
        values.put("q2", answer.getQ2());
        values.put("q3", answer.getQ3());
        values.put("q4", answer.getQ4());
        values.put("q5", answer.getQ5());
        values.put("q6", answer.getQ6());
        values.put("q7", answer.getQ7());
        values.put("q8", answer.getQ8());


        db.update("answer", values, "id = ?", new String[] {String.valueOf(answer.getId())});

        db.close();
    }

    public void deleteAnswer(int id){

    }
}
