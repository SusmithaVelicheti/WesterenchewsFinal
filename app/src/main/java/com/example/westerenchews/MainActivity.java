package com.example.westerenchews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.Logoutbutton);
        }
        public void gotoLoginActivity(View v) {
         Intent A = new Intent(this,LoginActivity.class);
         startActivity(A);
        }
    }