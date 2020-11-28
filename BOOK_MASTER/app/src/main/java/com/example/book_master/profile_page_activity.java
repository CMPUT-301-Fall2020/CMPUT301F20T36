package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.book_master.models.UserList;

/**
 * This activity class will show the user's username, contact info, & email and prompt a button to
 * allow user to edit his profile
 */
public class profile_page_activity extends AppCompatActivity {
    private Button edit, back;
    private TextView username, contact_info, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        edit = (Button) findViewById(R.id.profile_page_edit);
        back = (Button) findViewById(R.id.profile_page_back);
        username = (TextView) findViewById(R.id.profile_page_username);
        contact_info = (TextView) findViewById(R.id.profile_page_contact_info);
        email = (TextView) findViewById(R.id.profile_page_email);

        username.setText("  " +UserList.getCurrentUser().getUsername());
        contact_info.setText( "  " +UserList.getCurrentUser().getContactInfo());
        email.setText( "  " +UserList.getCurrentUser().getEmail());

        // close the activity and back to main menu
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // allow the user to go to edit profile page
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile_page_activity.this, edit_profile_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
