package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.book_master.models.*;

public class RetrieveProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_profile);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String userName = (String) bundle.getSerializable("userName");
        UserList userList = (UserList) bundle.getSerializable("userList");
    }
}