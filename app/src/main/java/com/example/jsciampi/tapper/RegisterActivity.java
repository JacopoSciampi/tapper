package com.example.jsciampi.tapper;

import android.accounts.Account;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;


public class RegisterActivity extends AppCompatActivity {
    //Form vars
    EditText username;
    EditText password;
    EditText email;
    TextView logText;

    //Awesome vars
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRegisterClick(View view) {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email    = findViewById(R.id.email);
        logText  = findViewById(R.id.logText);

        logText.setText("Throwing bananas to the server...");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.jacoposciampi.it/tapper/register.php?u=" + username.getText() + "&p=" +
                password.getText() + "&m=" +email.getText();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        if(1 == 1){
                            try{
                                    File path = context.getFilesDir();
                                    FileWriter fw = new FileWriter(path + "/username.dat");
                                    fw.write(username.getText().toString());
                                    fw.flush();
                                    fw.close();

                                    fw = new FileWriter(path + "/password.dat");
                                    fw.write(password.getText().toString());
                                    fw.flush();
                                    fw.close();
                                    logText.setText("Redirecting to the game :)");
                                } catch (Exception e){
                                    logText.setText("Cannot write in the json file");
                                    //TODO Handle exeption
                                }
                            //TODO: create a json object and then load game screen
                            //TODO: The json object is needed on login (if exists, then login directly
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

    public void onUsernameClick(View view) {
        username = findViewById(R.id.username);
        username.setText("");
    }
}