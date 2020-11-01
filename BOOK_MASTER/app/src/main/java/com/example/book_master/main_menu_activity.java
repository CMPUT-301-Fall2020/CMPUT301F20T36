package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_menu_activity extends AppCompatActivity {
    Button check_mylist_button;
    Button borrow_button;
    Button return_button;
    Button log_out_button;
    Button receive_button;
    Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        check_mylist_button = findViewById(R.id.CHECKMYLIST_button);
        borrow_button = findViewById(R.id.Borrow_main_button);
        return_button = findViewById(R.id.Return_main_button);
        log_out_button = findViewById(R.id.Logout_main_button);
        receive_button = findViewById(R.id.Receive_main_button);
        search_button = findViewById(R.id.Search_main_button);

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
                startActivity(log_out_intent);
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



    }
}