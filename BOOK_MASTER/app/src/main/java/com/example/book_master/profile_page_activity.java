package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile_page_activity extends AppCompatActivity {
    Button profile_back_retrive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrive_profile);

        profile_back_retrive = findViewById(R.id.Retrive_back);

        profile_back_retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_page_activity.this, main_menu_activity.class);
                startActivity(intent);
            }
        });
    }
}