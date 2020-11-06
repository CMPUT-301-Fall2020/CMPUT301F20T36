package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_master.models.DBHelper;
import com.example.book_master.models.UserList;

public class edit_profile_activity extends AppCompatActivity {
    private Button confirm;
    private TextView contact_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        confirm = (Button) findViewById(R.id.edit_profile_confirm);
        contact_info = (TextView) findViewById(R.id.edit_profile_contact_info);

        contact_info.setHint(UserList.getCurrentUser().getContactInfo());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_contact = contact_info.getText().toString();
                if (new_contact != null) {
                    UserList.getCurrentUser().setContactInfo(new_contact);
                    DBHelper.setUserDoc(UserList.getCurrentUser(), edit_profile_activity.this);

                    Intent intent = new Intent(edit_profile_activity.this, profile_page_activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
