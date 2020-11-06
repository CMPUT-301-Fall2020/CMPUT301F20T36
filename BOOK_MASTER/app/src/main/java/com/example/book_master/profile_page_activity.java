package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;

public class profile_page_activity extends AppCompatActivity {
    private Button edit, back;
    private TextView username, contact_info, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        edit = (Button) findViewById(R.id.profile_page_edit);
        back = (Button) findViewById(R.id.profile_page_back);
        username = (TextView) findViewById(R.id.profile_page_username);
        contact_info = (TextView) findViewById(R.id.profile_page_contact_info);
        email = (TextView) findViewById(R.id.profile_page_email);

        username.setText("Username: " + UserList.getCurrentUser().getUsername());
        contact_info.setText("Contact_info: " + UserList.getCurrentUser().getContactInfo());
        email.setText("Email: " + UserList.getCurrentUser().getEmail());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_page_activity.this, edit_profile_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
