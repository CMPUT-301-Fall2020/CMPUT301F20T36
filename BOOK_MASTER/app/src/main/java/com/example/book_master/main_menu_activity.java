package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.DBHelper;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;

import java.util.ArrayList;

/**
 * This activity class will be handle all switching activity. The user can click on the button
 * to direct to the page they wants
 */
public class main_menu_activity extends AppCompatActivity {
    private Button check_mylist_button;
    private Button borrow_button;
    private Button return_button;
    private Button log_out_button;
    private Button receive_button;
    private Button search_button;
    private Button edit_profile;
    private Button retrieve_profile;
    private Button request;
    private Button show_requested;
    private Button notification_bar_button;
    private TextView notification_bar_display;
    private Button show_borrowed;
    private static int notificationID;


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
        notification_bar_button = (Button) findViewById(R.id.main_menu_notification_bar_button);
        notification_bar_display = (TextView) findViewById(R.id.main_menu_notification_bar_display);
        request = (Button) findViewById(R.id.main_menu_request);
        show_requested = (Button) findViewById(R.id.main_menu_borrower_show_requested);
        show_borrowed = (Button) findViewById(R.id.main_menu_borrowed_list);

        notificationID = 0;
        search_not_shown_msg();

        String notification ="You have " + Integer.toString(MessageList.count_Message_Recieved(UserList.getCurrentUser().getUsername())) + " messages";
        notification_bar_display.setText(notification);

        // this will lead the user to view and edit the book one owned
        check_mylist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent check_list_intent = new Intent(main_menu_activity.this, check_list_activity.class);
                startActivity(check_list_intent);
            }
        });

        // this button is not implemented yet
        borrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_list_intent = new Intent(main_menu_activity.this, borrower_return_activity.class);
                startActivity(borrow_list_intent);
            }
        });

        // this button is not implemented
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_return_intent = new Intent(main_menu_activity.this, borrower_return_activity.class);
                startActivity(borrow_return_intent);
            }
        });

        // this button will log out the current user and ask user to log in
        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_out_intent = new Intent(main_menu_activity.this, MainActivity.class);
                DBHelper.signOut(main_menu_activity.this);
                startActivity(log_out_intent);
                finish();
            }
        });

        // This button did not implemented yet
        receive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent owner_receive_intent = new Intent(main_menu_activity.this, owner_receive_activity.class);
                startActivity(owner_receive_intent);
            }
        });

        // this button will lead the search book and ask to borrow.
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_page__intent = new Intent(main_menu_activity.this, search_page_activity.class);
                startActivity(search_page__intent);
            }
        });

        // this will let the user to see his own profile and edit
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_profile__intent = new Intent(main_menu_activity.this, profile_page_activity.class);
                startActivity(edit_profile__intent);
            }
        });

        // this button will let user to search for other usr
        retrieve_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retrieve_profile__intent = new Intent(main_menu_activity.this, retrieve_username_activity.class);
                startActivity(retrieve_profile__intent);
            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_menu_activity.this, request_menu.class);
                startActivity(intent);
            }
        });


        // this button will let borrower see what he has requested
        show_requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_list__intent = new Intent(main_menu_activity.this, borrower_requested_list_activity.class);
                startActivity(borrow_list__intent);
            }
        });

        notification_bar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_list__intent = new Intent(main_menu_activity.this, show_notification_activity.class);
                startActivity(borrow_list__intent);
            }
        });

        show_borrowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent borrow_list__intent = new Intent(main_menu_activity.this, borrower_borrowed_book_list_activity.class);
                startActivity(borrow_list__intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String notification ="You have " + Integer.toString(MessageList.count_Message_Recieved(UserList.getCurrentUser().getUsername())) + " messages";
        notification_bar_display.setText(notification);

        search_not_shown_msg();
    }

    private void search_not_shown_msg() {
        ArrayList<Message> message = MessageList.searchReceiver(UserList.getCurrentUser().getUsername());
        for (Message msg : message) {
            if (msg.getShownIndicator() != null && msg.getShownIndicator().equalsIgnoreCase(Message.NOTIFCATION_NOT_SHOWN)) {
                String title = "";
                String short_Message = "";
                String long_Message = "";

                if (msg.getStatus().equalsIgnoreCase(Book.REQUESTED)) {
                    title = "New Request";
                    short_Message = "Your book " + BookList.getBook(msg.getISBN()).getTitle() + " is requested by " + msg.getSender();
                    long_Message = short_Message;
                }
                else if (msg.getStatus().equalsIgnoreCase(Book.ACCEPTED)) {
                    title = "Request Accepted";
                    short_Message = "Your request for book " + BookList.getBook(msg.getISBN()).getTitle() + " is accepted by the owner";
                    long_Message = short_Message;
                }
                else if (msg.getStatus().equalsIgnoreCase(Book.BORROWED)) {
                    title = "Borrowing Begin!";
                    short_Message = "The book " + BookList.getBook(msg.getISBN()).getTitle() + " is handing to you!";
                    long_Message = "The owner of the book " + BookList.getBook(msg.getISBN()).getTitle() + "Is handing the book to you. You can comfirm the borrowing process by scan the ISBN.";
                }
                else if (msg.getStatus().equalsIgnoreCase(Book.RETURN)) {
                    title = "Borrowing Begin!";
                    short_Message = "The borrower of the book " + BookList.getBook(msg.getISBN()).getTitle() + " is handing the book to you!";
                    long_Message = "The borrower of the book " + BookList.getBook(msg.getISBN()).getTitle() + "Is handing the book to you. You can comfirm the returning process by scan the ISBN.";
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Book_master_channel_ID")
                        .setContentTitle(title)
                        .setContentText(short_Message)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(long_Message))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(notificationID, builder.build());
                notificationID++;
                msg.setShownIndicator(Message.NOTIFCATION_SHOWN);
            }
        }
    }
}
