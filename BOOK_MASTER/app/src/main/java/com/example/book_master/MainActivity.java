package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.book_master.models.DBHelper;
import com.example.book_master.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RegisterFrag.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper.userCollectionListener();
        DBHelper.bookCollectionListener();
        DBHelper.messageCollectionListener();

        final EditText emailText = findViewById(R.id.email_text);
        final EditText passwordText = findViewById(R.id.password_text);
        final Button loginBtn  = findViewById(R.id.login_button);
        final Button signUpBtn = findViewById(R.id.signUp_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.signIn(emailText.getText().toString(), passwordText.getText().toString(), MainActivity.this);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFrag
                        .newInstance(emailText.getText().toString(), passwordText.getText().toString())
                        .show(getSupportFragmentManager(), "Create_Account");
            }
        });
    }
}