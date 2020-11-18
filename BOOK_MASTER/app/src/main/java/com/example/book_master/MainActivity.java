package com.example.book_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.book_master.models.DBHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button   login;
    Button   sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        DBHelper.collectionListener();
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);

        username = findViewById(R.id.editTextTextPersonName2);
        password = findViewById(R.id.editTextTextPassword);

        login  = findViewById(R.id.login_button);
        sign_up = findViewById(R.id.sign_up_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.signIn(username.getText().toString(), password.getText().toString(), MainActivity.this);
//                Intent intent = new Intent(MainActivity.this, main_menu_activity.class);
//                startActivity(intent);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFrag
                        .newInstance(username.getText().toString(), password.getText().toString())
                        .show(getSupportFragmentManager(), "Create_Account");
            }
        });
    }
}