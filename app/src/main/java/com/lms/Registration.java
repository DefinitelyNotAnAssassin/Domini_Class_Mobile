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

public class Registration extends AppCompatActivity {


    EditText username, password, first_name, last_name;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.etStudentNumReg);
        password = findViewById(R.id.etPasswordReg);
        first_name = findViewById(R.id.etFNameReg);
        last_name = findViewById(R.id.etLNameReg);
        btnSignUp = findViewById(R.id.btnSignUp);

        String url = "http://dominiclass.pythonanywhere.com/register";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        btnSignUp.setOnClickListener(v -> {

            // request to the url


            // add parameters to the request


            StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                // handle the response
            }, error -> {
                // handle the error
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("first_name", first_name.getText().toString());
                    params.put("last_name", last_name.getText().toString());
                    return params;
                }
            };

            requestQueue.add(request);



        });


    }
}