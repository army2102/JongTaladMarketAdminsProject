package com.example.teerasaksathu.productionjongtalad.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.teerasaksathu.productionjongtalad.R;
import com.example.teerasaksathu.productionjongtalad.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences("user_token", Context.MODE_PRIVATE);
        String loginToken = prefs.getString("logined", null);


        if (loginToken != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", loginToken);
            startActivity(intent);
            finish();
        }

        else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, LoginFragment.newInstance())
                    .commit();
        }

    }


}



