package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.book_master.models.DBHelper;

public class main_menu_activity extends AppCompatActivity {
    private Button check_mylist_button;
    private Button borrow_button;
    private Button return_button;
    private Button log_out_button;
    private Button receive_button;
    private Button search_button;
    private Button edit_profile;
    private Button retrieve_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        check_mylist_button = (Button) findViewById(R.id.CHECKMYLIST_button);
        borrow_button = (Button) findViewById(R.id.Borrow_main_button);
        return_button = (Button) findViewById(R.id.Return_main_button);
        log_out_button = (Button) findViewById(R.id.Logout_main_button);
        receive_button = (Button) findViewById(R.id.Receive_main_button);
        search_button = (Button) findViewById(R.id.Search_main_button);
        edit_profile = (Button) findViewById(R.id.view_profile_button);
        retrieve_profile = (Button) findViewById(R.id.main_menu_retrieve_profile);

        check_mylist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent check_list_intent = new Intent(main_menu_activity.this, check_list_activity.class);
                startActivity(check_list_intent);
            }
        });

        borrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_list_intent = new Intent(main_menu_activity.this, borrow_list_activity.class);
                startActivity(borrow_list_intent);
            }
        });

        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_return_intent = new Intent(main_menu_activity.this, borrow_return_activity.class);
                startActivity(borrow_return_intent);
            }
        });

        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_out_intent = new Intent(main_menu_activity.this, MainActivity.class);
                DBHelper.signOut(main_menu_activity.this);
                startActivity(log_out_intent);
                finish();
            }
        });

        receive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent owner_receive_intent = new Intent(main_menu_activity.this, owner_receive_activity.class);
                startActivity(owner_receive_intent);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_page__intent = new Intent(main_menu_activity.this, search_page_activity.class);
                startActivity(search_page__intent);
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_profile__intent = new Intent(main_menu_activity.this, profile_page_activity.class);
                startActivity(edit_profile__intent);
            }
        });

        retrieve_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retrieve_profile__intent = new Intent(main_menu_activity.this, retrieve_username_activity.class);
                startActivity(retrieve_profile__intent);
            }
        });

    }
}