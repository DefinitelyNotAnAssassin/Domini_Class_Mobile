package com.lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class ActivityPage extends AppCompatActivity {

    String activityId;
    String currentUser;
    TextView tvActivityTitleCustomActPage;
    TextView tvQuestionCustomActPage;
    EditText etAnswerCustomActPage;
    Button btnSubmitCustomActPage;

    DrawerLayout drawer_Layout;
    ImageButton button_drawer_toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvActivityTitleCustomActPage = findViewById(R.id.tvActivityTitleCustomActPage);
        tvQuestionCustomActPage = findViewById(R.id.tvQuestionCustomActPage);
        etAnswerCustomActPage = findViewById(R.id.etAnswerCustomActPage);
        btnSubmitCustomActPage = findViewById(R.id.btnSubmitCustomActPage);


        Intent intent = getIntent();
        activityId = intent.getStringExtra("activityId");
        currentUser = intent.getStringExtra("currentUser");


;
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
                    Intent intent = new Intent(ActivityPage.this, CoursesMain.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Enroll Course")) {
                    Intent intent = new Intent(ActivityPage.this, EnrollCourse.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Finished Activities")) {
                    Intent intent = new Intent(ActivityPage.this, FinishedActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Missing Activities")) {
                    Intent intent = new Intent(ActivityPage.this, MissingActivities.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                } else if (id.equals("Log Out")) {
                    Intent intent = new Intent(ActivityPage.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (id.equals("Grades")){
                    Intent intent = new Intent(ActivityPage.this, ViewGrades.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ActivityPage.this, "Invalid Option", Toast.LENGTH_SHORT).show();
                }


                // Close the navigation drawer when an item is selected.

                return true;
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dominiclass.pythonanywhere.com/course/activity/" + activityId;

        StringRequest request = new StringRequest(
                com.android.volley.Request.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject activity = new JSONObject(response);
                        tvActivityTitleCustomActPage.setText(activity.getString("activity_name"));
                        tvQuestionCustomActPage.setText(activity.getString("activity_description"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );

        queue.add(request);


        btnSubmitCustomActPage.setOnClickListener(v -> {
            String answer = etAnswerCustomActPage.getText().toString();
            String url1 = "http://dominiclass.pythonanywhere.com/course/submitActivity/" + activityId;

            StringRequest request1 = new StringRequest(
                    com.android.volley.Request.Method.POST,
                    url1,
                    response -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                                Intent intent1 = new Intent(ActivityPage.this, CoursesMain.class);
                                intent1.putExtra("currentUser", currentUser);
                                startActivity(intent1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("currentUser", currentUser);
                    params.put("formData", answer);
                    // add more parameters as needed
                    return params;
                }


            };
            queue.add(request1);
        });

    }
}