package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.book_master.models.*;

/**
 * This activity class will show the user profile without the edit button
 */
public class retrieve_profile extends AppCompatActivity {
    TextView name, contact, email;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrive_profile);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        User user = (User) bundle.getSerializable("user");

        name = findViewById(R.id.user_name_retrieve);
        contact = findViewById(R.id.contact_retrieve);
        email = findViewById(R.id.email_retrieve);
        back = (Button) findViewById(R.id.Retrieve_back);

        name.setText(user.getUsername());
        contact.setText(user.getContactInfo());
        email.setText(user.getEmail());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}