package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class check_list_activity extends AppCompatActivity {
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_my_list);

        add_button = findViewById(R.id.check_list_add);

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent check_list_intent = new Intent(check_list_activity.this, add_book_activity.class);
                startActivity(check_list_intent);
            }
        });
        }
}


