package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.book_master.models.DBHelper;

public class LoginActivity extends AppCompatActivity implements RegisterFrag.OnFragmentInteractionListener {
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDBHelper = new DBHelper(LoginActivity.this);

        final EditText emailText = (EditText) findViewById(R.id.email_login);
        final EditText passwordText = (EditText) findViewById(R.id.password_login);

        final Button loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDBHelper.signIn(emailText.getText().toString(), passwordText.getText().toString());
            }
        });

        final Button createAccountBtn = findViewById(R.id.btn_createAccount);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFrag
                        .newInstance(emailText.getText().toString(), passwordText.getText().toString())
                        .show(getSupportFragmentManager(), "Create_Account");
            }
        });
    }
}