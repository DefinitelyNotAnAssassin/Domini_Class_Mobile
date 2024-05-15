package com.lms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class CoursesMain extends AppCompatActivity {

    ConstraintLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    Button btnSwitchToEnrollCourse;
    Button btnSwitchToCourseList;
    String currentUser;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_courses_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        currentUser = i.getStringExtra("currentUser");


        btnSwitchToEnrollCourse = findViewById(R.id.btnSwitchToEnrollCourse);
        btnSwitchToCourseList = findViewById(R.id.btnSwitchToCourseList);

        btnSwitchToEnrollCourse.setOnClickListener(v -> {
            Intent intent = new Intent(CoursesMain.this, EnrollCourse.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        });

        btnSwitchToCourseList.setOnClickListener(v -> {
            Intent intent = new Intent(CoursesMain.this, CourseList.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        });








    }


    }
