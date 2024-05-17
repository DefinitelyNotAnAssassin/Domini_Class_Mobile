package com.lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnrollCourse extends AppCompatActivity {


    EditText etCourseId;
    Button btnSwitchToJoinCourse;
    String currentUser;

    DrawerLayout drawer_Layout;
    ImageButton button_drawer_toggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enroll_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();


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
                    Intent intent = new Intent(EnrollCourse.this, CoursesMain.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Enroll Course")) {
                    Intent intent = new Intent(EnrollCourse.this, EnrollCourse.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Finished Activities")) {
                    Intent intent = new Intent(EnrollCourse.this, FinishedActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Missing Activities")) {
                    Intent intent = new Intent(EnrollCourse.this, MissingActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Log Out")) {
                    Intent intent = new Intent(EnrollCourse.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (id.equals("Grades")){
                    Intent intent = new Intent(EnrollCourse.this, ViewGrades.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(EnrollCourse.this, "Invalid Option", Toast.LENGTH_SHORT).show();
                }


                // Close the navigation drawer when an item is selected.

                return true;
            }
        });
        currentUser = i.getStringExtra("currentUser");

        etCourseId = findViewById(R.id.etCourseId);
        btnSwitchToJoinCourse = findViewById(R.id.btnSwitchToJoinCourse);


        RequestQueue queue = Volley.newRequestQueue(this);

        btnSwitchToJoinCourse.setOnClickListener(v -> {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    "http://dominiclass.pythonanywhere.com/course/enroll/",
                    response -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            System.out.println(jsonResponse);

                            if (jsonResponse.getString("message").equals("Course does not exist")) {
                                Toast.makeText(EnrollCourse.this, "Course does not exist", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else{
                                Intent intent = new Intent(EnrollCourse.this, CourseList.class);
                                intent.putExtra("currentUser", currentUser);
                                startActivity(intent);
                            }



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
                    params.put("course_code", etCourseId.getText().toString());
                    params.put("currentUser", currentUser);
                    return params;
                }
            };

            queue.add(request);
        });

    }
}