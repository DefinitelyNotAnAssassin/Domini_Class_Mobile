package com.lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
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


public class CoursePage extends AppCompatActivity {


    String courseId;
    String currentUser;


    ArrayList<Map<String, String>> courseMaterials = new ArrayList<>();
    ArrayList<Map<String, String>> courseActivities = new ArrayList<>();

    NavigationView navigationView;
    ListView lvForLessons;
    ListView lvForActivities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        courseId = getIntent().getStringExtra("courseId");
        currentUser = getIntent().getStringExtra("currentUser");
        lvForLessons = findViewById(R.id.lvForLessons);
        lvForActivities = findViewById(R.id.lvForActivities);
        RequestQueue queue = Volley.newRequestQueue(this);

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
                    Intent intent = new Intent(CoursePage.this, CoursesMain.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Enroll Course")) {
                    Intent intent = new Intent(CoursePage.this, EnrollCourse.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Finished Activities")) {
                    Intent intent = new Intent(CoursePage.this, FinishedActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Missing Activities")) {
                    Intent intent = new Intent(CoursePage.this, MissingActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Log Out")) {
                    Intent intent = new Intent(CoursePage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (id.equals("Grades")){
                    Intent intent = new Intent(CoursePage.this, ViewGrades.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(CoursePage.this, "Invalid Option", Toast.LENGTH_SHORT).show();
                }


                // Close the navigation drawer when an item is selected.

                return true;
            }
        });

        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://dominiclass.pythonanywhere.com/course/getCourse/"+courseId,
                response -> {
                    try {
                        // Parse the JSON response
                        JSONObject jsonResponse = new JSONObject(response);
                        System.out.println(jsonResponse);



                            // Get the course materials array
                            JSONArray courseMaterialsArray = jsonResponse.getJSONArray("course_materials");
                            for (int i = 0; i < courseMaterialsArray.length(); i++) {
                                JSONObject courseMaterial = courseMaterialsArray.getJSONObject(i);
                                Map<String, String> courseMaterialMap = new HashMap<>();
                                // Assuming the course material object has fields "id", "name", "description"
                                courseMaterialMap.put("id", courseMaterial.getString("id"));
                                courseMaterialMap.put("name", courseMaterial.getString("material_name"));
                                courseMaterialMap.put("description", courseMaterial.getString("material_description"));
                                courseMaterials.add(courseMaterialMap);
                            }




                            // Get the course activities array
                            JSONArray courseActivitiesArray = jsonResponse.getJSONArray("activities");
                            for (int i = 0; i < courseActivitiesArray.length(); i++) {
                                JSONObject courseActivity = courseActivitiesArray.getJSONObject(i);
                                Map<String, String> courseActivityMap = new HashMap<>();
                                // Assuming the course activity object has fields "id", "name", "description"
                                courseActivityMap.put("id", courseActivity.getString("id"));
                                courseActivityMap.put("activityName", courseActivity.getString("activity_name"));
                                courseActivityMap.put("activityDescription", courseActivity.getString("activity_description"));
                                courseActivities.add(courseActivityMap);
                            }



                        lvForActivities.setAdapter(new CustomCurrentActivityAdapter(this, courseActivities, currentUser));
                        lvForLessons.setAdapter(new CustomCurrentCourseLessonAdapter(this, courseMaterials, currentUser));
                        System.out.println(courseMaterials);
                        System.out.println(courseActivities);

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

                return params;
            }
        };

        queue.add(request);




    }





}
