package com.example.jsciampi.tapper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import com.example.jsciampi.tapper.utility.*;

public class MainActivity extends AppCompatActivity implements  AsyncResponse{
    // Generic vars
    public boolean isServerOnline;
    Context context = this;
    ServerStatus ss = new ServerStatus();

    // Form vars
    TextView bottomText;
    ImageButton loginButton;
    ImageButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomText = findViewById(R.id.startup_text_bottom);
        ss.delegate = this;
        ss.execute();
    }

    @Override
    public void processFinished(Boolean status) {
        isServerOnline = status;
        if(isServerOnline == true){
            bottomText.setText("Current build: ALPHA 1.0.0.0");
            DoAutoLogin();
        }else{
            bottomText.setText("Server is offline. Tap to try again");
        }
    }

    private void DoAutoLogin(){
        String username;
        String password;

        File f = new File(context.getFilesDir() + "/username.dat");
        File g = new File(context.getFilesDir() + "/password.dat");

        if(f.exists() && !f.isDirectory() && isServerOnline == true && g.exists() && !g.isDirectory()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(context.getFilesDir() + "/username.dat"));
                BufferedReader b2 = new BufferedReader(new FileReader(context.getFilesDir() + "/password.dat"));
                try{
                    StringBuilder sb = new StringBuilder();
                    StringBuilder s2 = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        line = br.readLine();
                    }

                    username = sb.toString();

                    String lin = b2.readLine();
                    while (line != null){
                        s2.append(lin);
                        line = b2.readLine();
                    }

                    password = s2.toString();
                    executeLogin(username,password);

                }catch(Exception e){
                    bottomText.setText("Internal file error, relog");
                }
            } catch (Exception e) {
                bottomText.setText("Config file lost, relog!");
            }
        } else {
            showButtons();
        }
    }

    private void executeLogin(String u, String p){
        bottomText.setText("Verifying login...");

    }

    private void showButtons(){
        loginButton = findViewById(R.id.login_button);
        registerButton =findViewById(R.id.register_button);
        loginButton.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
    }

    public void register_click(View view) {
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);

        try{
            startActivity(i);
        } catch (Exception e){
            bottomText.setText("Application internal error");
        }
    }

    public void onCheckServerStatusClick(View view) {
        if(isServerOnline == false){
            bottomText.setText("Checking server status...");
            try{
                ServerStatus temp = new ServerStatus();
                temp.delegate = this;
                temp.execute();
            } catch (Exception e){
                bottomText.setText("Internal error, reopen the app.");
            }

        }
    }
}
