package com.example.gardenkey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ForgotPasswordActivity extends AppCompatActivity
{
    private String check = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        check = getIntent().getStringExtra("check");
    }

    @Override
    protected void onStart()
    {
        super.onStart();


        if (check.equals("settings"))
        {

        }
        else if (check.equals("login"))
        {

        }
    }
}
