package com.example.draga.creditscoreapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class UserEntry {
    private DatabaseHelper dbh;

    /**
     * Constructor
     * @param context Context to run the instance in
     */
    public UserEntry(Context context) {
        // Initialize the DatabaseHelper with the supplied context
        dbh = new DatabaseHelper(context);
    }

    /**
     * Add a new user to the DB
     * @param user UserModel to be added
     */
    public void addUser(UserModel user) {
        Log.d("[DEBUG]", user.toString());

        // Get a writeable DB instance
        SQLiteDatabase db = dbh.getWritableDatabase();

        // Set up an object to hold the values of the user to be inserted
        ContentValues values = new ContentValues();
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        // Insert the user into the DB
        db.insert("user", null, values);

        // Close the DB instance
        db.close();
    }

    /**
     * Get all of the users from the DB
     * @return All of the users as a List<UserModel>
     */
    public List<UserModel> getAllUsers() {
        List<UserModel> users = new LinkedList<UserModel>();

        String query = "SELECT * FROM user"; // Query to execute

        SQLiteDatabase db = dbh.getWritableDatabase(); // Get a writeable database instance

        Cursor c = db.rawQuery(query, null); // Set up the cursor

        // Add all of the users to the list
        UserModel user = null;
        if(c.moveToFirst()) {
            do {
                user = new UserModel();
                user.setId(Integer.parseInt(c.getString(0)));
                user.setFirstName(c.getString(1));
                user.setLastName(c.getString(2));
                user.setEmail(c.getString(3));
                user.setPassword(c.getString(4));

                users.add(user);
            } while(c.moveToNext());
        }

        Log.d("[DEBUG]", users.toString());

        return users;
    }

    public int getIdByEmail(String email) {
        List<UserModel> users = getAllUsers();
        for (UserModel u : users) {
            if (u.getEmail()!= null && u.getEmail().equalsIgnoreCase(email)) {
                return u.getId();
            }
        }
        return -1;
    }

    /**
     * Used to get a single UserModel from the DB
     * @param id Integer ID of the user to return
     * @return A UserModel with the ID of the requested user
     */
    public UserModel getUser(Integer id) {
        List<UserModel> users = getAllUsers(); // Get all the users

        // Loop through the users
        for(UserModel u: users) {
            if(u.getId() == id) { // If we find a match
                return u; // Return that user
            }
        }

        // If there us no match, return a generic user
        return new UserModel();
    }

    public boolean hasEmail(String email) {
        List<UserModel> users = getAllUsers();

        for(UserModel u: users) {
            if(u.getEmail() != null && email != null) {
                System.out.println(email + "..." + u.getEmail());
                if(u.getEmail().equalsIgnoreCase(email)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getPasswordByEmail(String email) {
        List<UserModel> users = getAllUsers();

        System.out.println("users: " + users.toString());

        for(UserModel u: users) {
            if(u.getEmail() != null && email != null) {
                if(u.getEmail().equalsIgnoreCase(email)) {
                    System.out.println(email + "\t" + u.getEmail());
                    return u.getPassword();
                }
            }
        }

        return "Unable to find password for: " + email;
    }
}
