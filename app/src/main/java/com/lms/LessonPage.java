package com.lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class LessonPage extends AppCompatActivity {


    String lessonId;
    TextView tvActivityTitleLessonPage;
    TextView tvQuestionLessonPage;


    DrawerLayout drawer_Layout;
    ImageButton button_drawer_toggle;
    NavigationView navigationView;
    String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        lessonId = intent.getStringExtra("lessonId");
        currentUser = intent.getStringExtra("currentUser");


        tvActivityTitleLessonPage = findViewById(R.id.tvActivityTitleLessonPage);
        tvQuestionLessonPage = findViewById(R.id.tvQuestionLessonPage);


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
                    Intent intent = new Intent(LessonPage.this, CoursesMain.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Enroll Course")) {
                    Intent intent = new Intent(LessonPage.this, EnrollCourse.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Finished Activities")) {
                    Intent intent = new Intent(LessonPage.this, FinishedActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Missing Activities")) {
                    Intent intent = new Intent(LessonPage.this, MissingActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Log Out")) {
                    Intent intent = new Intent(LessonPage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (id.equals("Grades")){
                    Intent intent = new Intent(LessonPage.this, ViewGrades.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LessonPage.this, "Invalid Option", Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dominiclass.pythonanywhere.com/course/lesson/" + lessonId;

        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject lesson = new JSONObject(response);
                        tvActivityTitleLessonPage.setText(lesson.getString("material_name"));
                        tvQuestionLessonPage.setText(lesson.getString("material_description"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );

        queue.add(request);



    }
}