package com.example.jsciampi.tapper;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileWriter;

public class LoginActivity extends AppCompatActivity {
    //Vars
    Context context = this;

    //Form vars
    EditText username;
    EditText password;
    TextView logText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginClick(View view) {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        logText  = findViewById(R.id.logText);

        logText.setText("Executing login...");

        String u = username.getText().toString();
        String p = password.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.jacoposciampi.it/tapper/login.php?u=" + username.getText() + "&p=" +
                password.getText();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("ok")){
                            File path = context.getFilesDir();
                            try{
                                FileWriter fw = new FileWriter(path + "/username.dat");
                                fw.write(username.getText().toString());
                                fw.flush();
                                fw.close();

                                fw = new FileWriter(path + "/password.dat");
                                fw.write(password.getText().toString());
                                fw.flush();
                                fw.close();
                            } catch (Exception e){
                                logText.setText("Cannot create config file.");
                            }

                            logText.setText("Awesome! :)");
                        } else {
                            logText.setText(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logText.setText("Internal error");
            }
        });

        queue.add(request);
    }

    public void onBackClick(View view) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);

        try{
            startActivity(i);
        } catch (Exception e){
            logText.setText("Application internal error");
        }
    }
}
