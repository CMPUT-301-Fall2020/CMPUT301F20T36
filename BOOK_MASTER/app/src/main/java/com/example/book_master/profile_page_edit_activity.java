package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profile_page_edit_activity extends AppCompatActivity {
    Button profile_edit_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        profile_edit_back = findViewById(R.id.profile_back_button);

        profile_edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_page_edit_activity.this, main_menu_activity.class);
                startActivity(intent);
            }
        });
    }
}