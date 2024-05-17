package com.lms;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinishedActivities extends AppCompatActivity {
    ListView lvFinishedActs;
    ArrayList<Map<String, String>> activities = new ArrayList<>();
    String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finished_activities);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        currentUser = getIntent().getStringExtra("currentUser");
        lvFinishedActs = findViewById(R.id.lvFinishedActs);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://dominiclass.pythonanywhere.com/course/getFinishedActivities",
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray courseArray = jsonResponse.getJSONArray("finished_activities");

                        System.out.println(courseArray);

                        for (int x = 0; x < courseArray.length(); x++) {
                            JSONObject course = courseArray.getJSONObject(x);
                            Map<String, String> courseMap = new HashMap<>();
                            courseMap.put("activityName", course.getString("activity_name"));
                            courseMap.put("courseName", course.getString("course_name"));

                            activities.add(courseMap);
                        }


                        CustomMissingActivityAdapter adapter = new CustomMissingActivityAdapter(this, activities, currentUser);
                        lvFinishedActs.setAdapter(adapter);


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