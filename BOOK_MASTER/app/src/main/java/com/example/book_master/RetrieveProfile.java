package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.book_master.models.*;

public class RetrieveProfile extends AppCompatActivity {
    TextView name, content, contact, phone, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrive_profile);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String userName = (String) bundle.getSerializable("userName");
        User user = UserList.get_User(userName);
        name = findViewById(R.id.user_name_retrieve);
        contact = findViewById(R.id.contact_retrieve);
        phone = findViewById(R.id.phone_retrieve);
        email = findViewById(R.id.email_retrieve);
        name.setText(user.getUserName());
        contact.setText(user.getContactInfo());
        email.setText(user.getEmail());
    }
}