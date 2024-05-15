package com.lms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnrollCourse extends AppCompatActivity {


    EditText etCourseId;
    Button btnSwitchToJoinCourse;


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

        etCourseId = findViewById(R.id.etCourseId);
        btnSwitchToJoinCourse = findViewById(R.id.btnSwitchToJoinCourse);


        RequestQueue queue = Volley.newRequestQueue(this);

        btnSwitchToJoinCourse.setOnClickListener(v -> {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    "http://192.168.1.6:8000/login",
                    response -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            System.out.println(jsonResponse);
                            Intent intent = new Intent(EnrollCourse.this, CourseList.class);
                            startActivity(intent);


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
                    return params;
                }
            };

            queue.add(request);
        });

    }
}