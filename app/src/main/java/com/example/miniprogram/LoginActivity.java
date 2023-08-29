package com.example.miniprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences =  getSharedPreferences("Myprefs",MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("Email","");
        String savedPassword = sharedPreferences.getString("Password","");
        if(!savedEmail.isEmpty() && !savedPassword.isEmpty()){
            DBHelper DB = new DBHelper(this);
            if(DB.checkUsernamePassword(savedEmail,savedPassword)){
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
            }
        }
        else {
            setContentView(R.layout.activity_login);
            sharedPreferences =  getSharedPreferences("Myprefs",MODE_PRIVATE);
            EditText email;
            EditText password;
            Button  login, signup;
            DBHelper DB;

            DB = new DBHelper(this);
            email = findViewById(R.id.editTextTextEmailAddress2);
            password = findViewById(R.id.editTextTextPassword3);
            login = findViewById(R.id.button3);
            signup = findViewById(R.id.button2);
            Bundle bund = getIntent().getExtras();

            if (bund != null) {
                String Email = bund.getString("Email");
                String Password = bund.getString("Password");
                email.setText(Email);
                password.setText(Password);
            }

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Email = email.getText().toString();
                    String Password = password.getText().toString();

                    if (Email.equals("") || Password.equals(""))
                        Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    else {
                        boolean checkUserPass = DB.checkUsernamePassword(Email, Password);
                        if (checkUserPass) {
                            Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Email", Email);
                            editor.putString("Password", Password);
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(in);

                }
            });
        }


    }
    @Override
    public void onBackPressed() {
        // Close the entire app when back button is pressed on the login screen
        finishAffinity();
    }
}