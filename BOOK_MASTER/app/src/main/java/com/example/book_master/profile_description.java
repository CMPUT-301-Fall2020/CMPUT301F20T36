package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_master.models.User;
import com.example.book_master.models.UserList;

/**
 * This activity class will show the user's username, contact info, & email and prompt a button to
 * allow user to edit his profile
 */
public class profile_description extends AppCompatActivity {
    private Button edit, back;
    private TextView username, contact_info, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User) bundle.getSerializable("User");

        edit = findViewById(R.id.profile_page_edit);
        edit.setVisibility(View.GONE);

        back = findViewById(R.id.profile_page_back);
        username = findViewById(R.id.profile_page_username);
        contact_info = findViewById(R.id.profile_page_contact_info);
        email = findViewById(R.id.profile_page_email);

        username.setText("  " +user.getUsername());
        contact_info.setText( "  " +user.getContactInfo());
        email.setText( "  " +user.getEmail());

        // close the activity and back to main menu
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}