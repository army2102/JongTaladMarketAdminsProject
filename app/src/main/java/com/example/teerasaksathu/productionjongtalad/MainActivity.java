package com.example.teerasaksathu.productionjongtalad;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegisterPage;
    private Button btnLoginPage;
    private TextView tvForgotPassword;
    private EditText etUsername;
    private EditText etPassword;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
        btnRegisterPage.setOnClickListener(this);
        btnLoginPage.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);


    }

    private class CheckLogin extends AsyncTask<String, Void, String> {

        private static final String URL = "http://www.jongtalad.com/doc/phpNew/login.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", values[0])
                    .add("password", values[1])
                    .build();

            Request.Builder builder = new Request.Builder();
            Request request = builder
                    .url(URL)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Not Success - code : " + response.code();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error - " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Post", "==>" + s);
            if (s.equals("1")) {
                Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LockReservation.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initInstances() {
        btnRegisterPage = (Button) findViewById(R.id.btnRegisterPage);
        btnLoginPage = (Button) findViewById(R.id.btnLoginPage);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

    }

    @Override
    public void onClick(View view) {
        if (view == btnLoginPage) {
            username = etUsername.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            CheckLogin checkLogin = new CheckLogin();
            checkLogin.execute(username, password);


        } else if (view == btnRegisterPage) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        } else if (view == tvForgotPassword) {
            Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
    }

}



