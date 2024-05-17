package com.lms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseList extends AppCompatActivity {




    ListView lvEnrolledCourseList;
    String currentUser;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    ArrayList<Map<String, String>> courses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            // Handle the actions for the different menu items here

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });



        Intent i = getIntent();
        currentUser = i.getStringExtra("currentUser");

        lvEnrolledCourseList = findViewById(R.id.lvEnrolledCourseList);




        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://dominiclass.pythonanywhere.com/course/getCourse",
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray courseArray = jsonResponse.getJSONArray("course");

                        System.out.println(courseArray);

                        for (int x = 0; x < courseArray.length(); x++) {
                            JSONObject course = courseArray.getJSONObject(x);
                            Map<String, String> courseMap = new HashMap<>();
                            courseMap.put("courseName", course.getString("course_name"));
                            courseMap.put("courseDescription", course.getString("course_description"));
                            courseMap.put("couresCode", course.getString("course_code"));
                            courseMap.put("id", course.getString("id"));
                            courses.add(courseMap);
                        }


                        CustomCoursesItemAdapter adapter = new CustomCoursesItemAdapter(this, courses, currentUser);
                        lvEnrolledCourseList.setAdapter(adapter);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println(error);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("currentUser", currentUser);
                return params;
            }
        };

        queue.add(request);




    }




}