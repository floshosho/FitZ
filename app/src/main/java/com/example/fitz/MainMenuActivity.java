package com.example.fitz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }
}
