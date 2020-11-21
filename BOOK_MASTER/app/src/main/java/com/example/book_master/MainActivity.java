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

/**
 * This activity class is used to handle login and sign up stage
 */
public class MainActivity extends AppCompatActivity implements RegisterFrag.OnFragmentInteractionListener {
    private EditText emailText;
    private EditText passwordText;
    private Button loginBtn;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper.userCollectionListener();
        DBHelper.bookCollectionListener();
        DBHelper.messageCollectionListener();

        final EditText emailText = (EditText) findViewById(R.id.email_text);
        final EditText passwordText = (EditText) findViewById(R.id.password_text);
        Button loginBtn  = findViewById(R.id.login_button);
        Button signUpBtn = findViewById(R.id.signUp_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            // the sign in and intent start will be handled by DBhelper
            @Override
            public void onClick(View v) {
                DBHelper.signIn(emailText.getText().toString(),
                        passwordText.getText().toString(),
                        MainActivity.this);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            // the sign up will be handled by DBhelper, then user should login from MainActivity
            @Override
            public void onClick(View v) {
                RegisterFrag
                        .newInstance(emailText.getText().toString(),
                                passwordText.getText().toString())
                        .show(getSupportFragmentManager(), "Create_Account");
            }
        });
    }
}