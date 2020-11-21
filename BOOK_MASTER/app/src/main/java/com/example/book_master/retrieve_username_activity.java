package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.book_master.models.UserList;

/**
 * This activity class will allow user to input the username which they wants to search
 * then the profile of that user will be shown in a seperate activity
 */
public class retrieve_username_activity extends AppCompatActivity {
    TextView username;
    Button confirm, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrieve_username);

        username = (TextView) findViewById(R.id.retrieve_profile_username);
        confirm = (Button) findViewById(R.id.retrieve_profile_confirm);
        back = (Button) findViewById(R.id.retrieve_profile_discard);;

        // go to the page which shows the searched user
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                if (name == null || UserList.getUser(name) == null) {
                    Toast.makeText(retrieve_username_activity.this, "The Person Does not Exist", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent retrieve_profile__intent = new Intent(retrieve_username_activity.this, RetrieveProfile.class);
                    retrieve_profile__intent.putExtra("userName", username.getText().toString());
                    startActivity(retrieve_profile__intent);
                    finish();
                }
            }
        });
        // back to main menu
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
