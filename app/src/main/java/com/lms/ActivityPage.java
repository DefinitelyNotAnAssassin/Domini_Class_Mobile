package com.lms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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