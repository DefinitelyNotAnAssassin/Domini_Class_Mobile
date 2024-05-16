package com.lms;

import android.os.Bundle;

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


public class CoursePage extends AppCompatActivity {


    String courseId;
    ArrayList<Map<String, String>> courseMaterials = new ArrayList<>();
    ArrayList<Map<String, String>> courseActivities = new ArrayList<>();
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

        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://192.168.1.6:8000/course/getCourse/"+courseId,
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
                                courseMaterialMap.put("name", courseMaterial.getString("name"));
                                courseMaterialMap.put("description", courseMaterial.getString("description"));
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
