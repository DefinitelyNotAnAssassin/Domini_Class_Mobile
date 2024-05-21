package com.lms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

    DrawerLayout drawer_Layout;
    ImageButton button_drawer_toggle;
    NavigationView navigationView;






    ArrayList<Map<String, String>> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_list);


        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                System.out.println("Clicked " + item);
                String id = item.toString();

                // if id.equals("home") then start an intent and activity
                // the following values are Home, Enroll Course, Finished Activities, Missing Activities, Log Out


                if (id.equals("Home")) {
                    Intent intent = new Intent(CourseList.this, CoursesMain.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Enroll Course")) {
                    Intent intent = new Intent(CourseList.this, EnrollCourse.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Finished Activities")) {
                    Intent intent = new Intent(CourseList.this, FinishedActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Missing Activities")) {
                    Intent intent = new Intent(CourseList.this, MissingActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Log Out")) {
                    Intent intent = new Intent(CourseList.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (id.equals("Grades")){
                    Intent intent = new Intent(CourseList.this, ViewGrades.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CourseList.this, "Invalid Option", Toast.LENGTH_SHORT).show();
                }


                // Close the navigation drawer when an item is selected.

                return true;
            }
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println("Clicked");
        return super.onOptionsItemSelected(item);
    }
}