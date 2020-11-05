package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.book_master.models.*;

public class RetrieveProfile extends AppCompatActivity {
    TextView name, contact, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrive_profile);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String userName = (String) bundle.getSerializable("userName");
        User user = UserList.getUser(userName);

        name = findViewById(R.id.user_name_retrieve);
        contact = findViewById(R.id.contact_retrieve);
        email = findViewById(R.id.email_retrieve);

        name.setText(user.getUsername());
        contact.setText(user.getContactInfo());
        email.setText(user.getEmail());
    }
}