package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_master.models.DBHelper;
import com.example.book_master.models.User;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userList = new ArrayList<>();

        userList = DBHelper.getUserList();

        Toast.makeText(this, userList.get(0).getUserName(), Toast.LENGTH_LONG).show();
        User currentUser = userList.get(0);
        final TextView emailText = (TextView) findViewById(R.id.email_profile);
        final TextView userNameText = (TextView) findViewById(R.id.username_profile);
        final TextView contactInfoText = (TextView) findViewById(R.id.contactInfo_profile);
        userNameText.setText(currentUser.getUserName());
        contactInfoText.setText(currentUser.getContactInfo());
    }
}