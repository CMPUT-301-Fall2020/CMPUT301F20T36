package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class add_book_activity extends AppCompatActivity {
    Button Discard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_book);

        Discard = findViewById(R.id.discard_buttom);

        Discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_book_activity.this, check_list_activity.class);
                startActivity(intent);
            }
        });
    }
}