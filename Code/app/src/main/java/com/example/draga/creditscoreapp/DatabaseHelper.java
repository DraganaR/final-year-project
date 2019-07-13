package com.example.draga.creditscoreapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final Integer DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "credit_score";

    // user: id, firstname, lastname, email, password
    private final String CREATE_USER_TABLE = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT, email TEXT, password TEXT)";
    private final String CREATE_ANSWER_TABLE = "CREATE TABLE answer(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INT, q1 TEXT, q2 TEXT, q3 TEXT, q4 TEXT, q5 TEXT,q6 TEXT, q7 TEXT, q8 TEXT)";

    // Columns in the user table
    private static final String[] COLUMNS_USER = {"id", "firstName", "lastName", "email", "password"};
    private static final String[] COLUMNS_ANSWER = {"id", "userId", "q1", "q2", "q3", "q4", "q5", "q6", "q7", "q8"};
    /**
     * Constructor
     * @param context Context to run the instance in
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ANSWER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS answer");

        this.onCreate(sqLiteDatabase);
    }

    public static Integer getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public String getCREATE_USER_TABLE() {
        return CREATE_USER_TABLE;
    }
    public String getCREATE_ANSWER_TABLE(){ return CREATE_ANSWER_TABLE;}

    public String[] getColumnsUser() {
        return COLUMNS_USER;
    }
    public String[] getColumnsAnswer() { return COLUMNS_ANSWER;}
}
