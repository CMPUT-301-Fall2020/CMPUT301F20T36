package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class request_menu extends AppCompatActivity {
    Button sent, received;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_menu);
        sent = findViewById(R.id.request_sent_button);
        received = findViewById(R.id.request_received_button);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(request_menu.this, view_accepted_request.class);
                startActivity(intent);
                finish();
            }
        });
        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(request_menu.this, request_list.class);
                startActivity(intent);
                finish();
            }
        });
    }
}