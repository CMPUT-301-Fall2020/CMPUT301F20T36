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

public class MainActivity extends AppCompatActivity {
    static ArrayList<User> userList= new ArrayList<>();
    EditText emailText;
    EditText passwordText;
    Button   loginBtn;
    Button   signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper.userCollectionListener();
        DBHelper.bookCollectionListener();
        DBHelper.messageCollectionListener();

        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);
        loginBtn  = findViewById(R.id.login_button);
        signUpBtn = findViewById(R.id.signUp_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, main_menu_activity.class);
                startActivity(intent);
            }
        });
    }
}