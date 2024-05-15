package com.lms;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etStudentNumLogin;
    EditText etPasswordLogin;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etStudentNumLogin = findViewById(R.id.etStudentNumLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        String username = etStudentNumLogin.getText().toString();
        String password = etPasswordLogin.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);




        btnLogin.setOnClickListener(v -> {
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    "http://192.168.1.6:8000/login",
                    response -> {

                    },
                    error -> {
                        System.out.println(error);
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", etStudentNumLogin.getText().toString());
                    params.put("password", etPasswordLogin.getText().toString());
                    return params;
                }
            };

            queue.add(request);
        });
    }



}