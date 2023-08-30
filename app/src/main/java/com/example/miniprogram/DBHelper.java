package com.example.miniprogram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Login.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_MOBILE_NUMBER = "mobile_number"; // New field
    public static final String COLUMN_EMAIL = "email"; // New field

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_MOBILE_NUMBER + " TEXT, " +
                COLUMN_EMAIL + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertData(String email, String password, String mobileNumber,String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_MOBILE_NUMBER, mobileNumber); // Insert mobile number
        contentValues.put(COLUMN_EMAIL, email); // Insert email
        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close(); // Close the database connection
        return result != -1;
    }

    public List<Student> getAllUsers() {
        List<Student> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USERNAME, COLUMN_MOBILE_NUMBER, COLUMN_EMAIL},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String mobileNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOBILE_NUMBER));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));

                Student student = new Student(username, mobileNumber, email);
                userList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }


    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        Log.d("DBHelper", "checkUsernamePassword: username = " + username + ", password = " + COLUMN_PASSWORD);
        Log.d("DBHelper", "checkUsernamePassword: Result count = " + cursor.getCount());

        cursor.close(); // Close the cursor
        db.close(); // Close the database connection
        return exists;
    }

    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_PASSWORD},
                COLUMN_EMAIL + "=?",
                new String[]{username}, null, null, null);

        boolean exists = false;

        if (cursor.moveToFirst()) {
            String dbPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            exists = dbPassword.equals(password);
        }

        cursor.close();
        db.close();
        return exists;
    }


    // ... Other methods if any ...
}
