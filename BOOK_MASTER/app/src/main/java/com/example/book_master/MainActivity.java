package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_master.fragment.register;
import com.example.book_master.models.DBHelper;

/**
 * This activity class is used to handle login and sign up stage
 */
public class MainActivity extends AppCompatActivity implements register.OnFragmentInteractionListener {
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

        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);
        loginBtn  = findViewById(R.id.activity_main_login);
        signUpBtn = findViewById(R.id.activity_main_signup);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            // the sign in and intent start will be handled by DBhelper
            @Override
            public void onClick(View v) {
                if (emailText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "The username is null", Toast.LENGTH_SHORT).show();
                }
                else if (passwordText.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "The password is null", Toast.LENGTH_SHORT).show();
                }
                else {
                    DBHelper.signIn(emailText.getText().toString(),
                            passwordText.getText().toString(),
                            MainActivity.this);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            // the sign up will be handled by DBhelper, then user should login from MainActivity
            @Override
            public void onClick(View v) {
                register.newInstance(emailText.getText().toString(),
                                passwordText.getText().toString())
                        .show(getSupportFragmentManager(), "Create_Account");
            }
        });
    }
}
