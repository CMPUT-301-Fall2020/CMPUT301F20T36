package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.book_master.models.DBHelper;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;

/**
 * This activity class will be handle all switching activity. The user can click on the button
 * to direct to the page they wants
 */
public class main_menu_activity extends AppCompatActivity {
    private Button check_myList_button;
    private Button borrow_button;
    private Button log_out_button;
    private Button search_button;
    private Button edit_profile;
    private Button request;
    private Button notification_bar_button;
    private TextView notification_bar_display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        check_myList_button = findViewById(R.id.CHECKMYLIST_button);
        borrow_button = findViewById(R.id.Borrow_main_button);
        log_out_button = findViewById(R.id.Logout_main_button);
        search_button = findViewById(R.id.Search_main_button);
        edit_profile = findViewById(R.id.view_profile_button);
        notification_bar_button = findViewById(R.id.main_menu_notification_bar_button);
        notification_bar_display = findViewById(R.id.main_menu_notification_bar_display);
        request = findViewById(R.id.main_menu_request);

         String notification ="You have " + MessageList.countMsgReceived(UserList.getCurrentUser().getUsername()) + " messages";
         notification_bar_display.setText(notification);

        check_myList_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent check_list_intent = new Intent(main_menu_activity.this, check_list_activity.class);
                startActivity(check_list_intent);
                overridePendingTransition(R.anim.fade, R.anim.anim1);
            }
        });

        // this button is not implemented yet
        borrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_list_intent = new Intent(main_menu_activity.this, borrower_return_activity.class);
                startActivity(borrow_list_intent);
                overridePendingTransition(R.anim.fade, R.anim.anim1);
            }
        });

        // this button will log out the current user and ask user to log in
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_out_intent = new Intent(main_menu_activity.this, MainActivity.class);
                DBHelper.signOut(main_menu_activity.this);
                startActivity(log_out_intent);
                overridePendingTransition(R.anim.fade, R.anim.anim1);
                finish();
            }
        });

        // this button will lead the search book and ask to borrow.
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_page__intent = new Intent(main_menu_activity.this, search_navigator.class);
                startActivity(search_page__intent);
                overridePendingTransition(R.anim.fade, R.anim.anim1);
            }
        });

        // this will let the user to see his own profile and edit
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_profile__intent = new Intent(main_menu_activity.this, profile_description_activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Visibility", 1);

                edit_profile__intent.putExtras(bundle);
                startActivity(edit_profile__intent);
                overridePendingTransition(R.anim.fade, R.anim.anim1);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_menu_activity.this, request_navigator.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.anim1);
            }
        });

        notification_bar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_list__intent = new Intent(main_menu_activity.this, show_notification_activity.class);
                startActivity(borrow_list__intent);
                overridePendingTransition(R.anim.fade, R.anim.anim1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String notification ="You have " + MessageList.countMsgReceived(UserList.getCurrentUser().getUsername()) + " messages";
        notification_bar_display.setText(notification);
    }
}
