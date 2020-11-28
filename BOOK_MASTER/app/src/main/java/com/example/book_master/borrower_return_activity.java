package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.DBHelper;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class borrower_return_activity extends AppCompatActivity{
    private Button Scann_Button;
    private TextView ISBN_Display;
    private Button HandOver;
    private String ISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_return);
        ISBN = "0.13882280795084379";

        Scann_Button = (Button) findViewById(R.id.borrrower_return_ISBNbutton);
        ISBN_Display = (TextView) findViewById(R.id.Borrower_return_name);
        HandOver = (Button) findViewById(R.id.Borrower_return_deliverButton);

        Scann_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(borrower_return_activity.this);
                integrator.setCaptureActivity(capture_ISBN__activity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning ISBN");
                integrator.initiateScan();
            }
        });

        HandOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ISBN != null) {
                    send_request();
                }
                else {
                    Toast.makeText(borrower_return_activity.this, "The Book Is Not Scanned!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                ISBN = scanningResult.getContents();
                ISBN_Display.setText("ISBN: " + ISBN);
            }
            else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    private void send_request() {
        if (BookList.getBook(ISBN) != null) {
            Book book = BookList.getBook(ISBN);
            String current_username = UserList.getCurrentUser().getUsername();
            if (current_username.equalsIgnoreCase(book.getBorrower())) {
                if (book.getStatus().equalsIgnoreCase(Book.CONFIRM_BORROWED)) {
                    Message msg = new Message(book.getOwner(), book.getBorrower(), ISBN, Book.RETURN, "", "", Message.NOTIFICATION_NOT_SHOWN);
                    book.setStatus(Book.RETURN);
                    DBHelper.setBookDoc(book.getISBN(), book, borrower_return_activity.this);
                    DBHelper.setMessageDoc(Integer.toString(msg.hashCode()), msg, borrower_return_activity.this);
                    finish();
                    return;
                }
                else if (book.getStatus().equalsIgnoreCase(Book.BORROWED)) {
                    ArrayList<Message> msglist = MessageList.searchReceiver(current_username);
                    for (Message msg : msglist) {
                        if (msg.getISBN().equalsIgnoreCase(book.getISBN())) {
                            DBHelper.deleteMessageDoc(Integer.toString(msg.hashCode()),borrower_return_activity.this);
                            book.setStatus(Book.CONFIRM_BORROWED);
                            DBHelper.setBookDoc(book.getISBN(), book, borrower_return_activity.this);
                            finish();
                            return;
                        }
                    }
                    Toast.makeText(this, "There is an error with internal system", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "The book is not in the correct status", Toast.LENGTH_SHORT).show();
                }
            } else if (current_username.equalsIgnoreCase(book.getOwner())) {
                if (book.getStatus().equalsIgnoreCase(Book.RETURN)) {
                    ArrayList<Message> msglist = MessageList.searchReceiver(current_username);
                    for (Message msg : msglist) {
                        if (msg.getISBN().equalsIgnoreCase(book.getISBN())) {
                            DBHelper.deleteMessageDoc(Integer.toString(msg.hashCode()),borrower_return_activity.this);
                            book.setStatus(Book.AVAILABLE);
                            DBHelper.setBookDoc(book.getISBN(), book, borrower_return_activity.this);
                            finish();
                            return;
                        }
                    }
                    Toast.makeText(this, "There is an error with internal system", Toast.LENGTH_SHORT).show();
                }
                else if (book.getStatus().equalsIgnoreCase(Book.ACCEPTED)) {
                    Message msg = new Message(book.getOwner(), book.getBorrower(), ISBN, Book.BORROWED, "", "", Message.NOTIFICATION_NOT_SHOWN);
                    book.setStatus(Book.BORROWED);
                    book.setBorrower("");
                    DBHelper.setBookDoc(book.getISBN(), book, borrower_return_activity.this);
                    DBHelper.setMessageDoc(Integer.toString(msg.hashCode()), msg, borrower_return_activity.this);
                    finish();
                    return;
                }else {
                    Toast.makeText(this, "The book is not in the correct status", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "You are not the current borrower", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "The book is not existed", Toast.LENGTH_SHORT).show();
        }
    }
}
