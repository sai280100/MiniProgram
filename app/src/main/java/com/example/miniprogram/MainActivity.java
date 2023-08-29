package com.example.miniprogram;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.BreakIterator;
import java.time.Instant;

public class MainActivity extends AppCompatActivity {
    EditText fullName,mobNo;
    EditText email;
    EditText password, confirmPassword;
    Button register;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullName = findViewById(R.id.editTextText);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextPassword2);
        register = findViewById(R.id.button);
        mobNo = findViewById(R.id.editTextPhone);
        DB = new DBHelper(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = fullName.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String ConfirmPassword = confirmPassword.getText().toString();
                String Mob = mobNo.getText().toString();

                if (user.isEmpty() || !user.matches("[a-zA-Z]+")) {
                    fullName.setError("Name must have only characters");
                    return;
                }

                if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Invalid email address");
                    return;
                }
                if (Mob.isEmpty() || !Patterns.PHONE.matcher(Mob).matches()) {
                    mobNo.setError("Invalid mobile number");
                    return;
                }

                if (Password.isEmpty() || Password.length() < 8) {
                    password.setError("Password must have minimum 8 characters");
                    return;
                }
                if (!ConfirmPassword.equals(Password)) {
                    confirmPassword.setError("Not matched with password");
                    return;
                }
                if (Email.equals("") || Password.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (Password.length() != 0) {
                        boolean checkUser = DB.checkUsername(user);

                        if (!checkUser) {
                            Boolean insert = DB.insertData(Email, Password,Mob,user);
                            if (insert) {
                                Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                                Bundle bun = new Bundle();
                                if (bun != null) {
                                    bun.putString("Email", Email);
                                    bun.putString("Password", Password);
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    intent.putExtras(bun);
                                    startActivity(intent);
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }


        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }
}