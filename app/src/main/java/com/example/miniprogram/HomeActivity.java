package com.example.miniprogram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Student> studentList = getAllRegisteredStudents();
        studentAdapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(studentAdapter);
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logout here
                // For example, clear saved preferences and navigate to the login screen
                SharedPreferences sharedPreferences = getSharedPreferences("Myprefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });
    }



    private List<Student> getAllRegisteredStudents() {
        List<Student> studentList = new ArrayList<>();

        // Retrieve all users from the database
        List<Student> users = dbHelper.getAllUsers();

        for (Student user : users) {
            String fullName = user.getFullName();
            String mobileNumber = user.getMobileNumber();
            String email = user.getEmail();
            studentList.add(new Student(fullName, mobileNumber, email));
        }

        return studentList;
    }


}
