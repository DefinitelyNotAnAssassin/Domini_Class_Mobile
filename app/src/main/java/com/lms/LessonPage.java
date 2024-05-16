package com.lms;

import android.content.Intent;
import android.os.Bundle;
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

public class LessonPage extends AppCompatActivity {


    String lessonId;
    TextView tvActivityTitleLessonPage;
    TextView tvQuestionLessonPage;
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


        tvActivityTitleLessonPage = findViewById(R.id.tvActivityTitleLessonPage);
        tvQuestionLessonPage = findViewById(R.id.tvQuestionLessonPage);

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