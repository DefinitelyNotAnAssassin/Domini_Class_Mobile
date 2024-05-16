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


public class Login extends AppCompatActivity {



    EditText etStudentNumLogin;
    EditText etPasswordLogin;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



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
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            System.out.println(jsonResponse);

                            if(jsonResponse.get("message").equals("Login successful")) {
                                Intent intent = new Intent(Login.this, CoursesMain.class);
                                intent.putExtra("currentUser", jsonResponse.get("currentUser").toString() );
                                startActivity(intent);
                            }
                            else{
                                // show toast message

                                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
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
                    params.put("username", etStudentNumLogin.getText().toString());
                    params.put("password", etPasswordLogin.getText().toString());
                    return params;
                }
            };

            queue.add(request);
        });
    }
}