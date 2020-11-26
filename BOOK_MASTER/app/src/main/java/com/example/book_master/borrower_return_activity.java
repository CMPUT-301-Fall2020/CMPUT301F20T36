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
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class borrower_return_activity extends AppCompatActivity implements View.OnClickListener{
    private Button Scann_Button;
    private TextView ISBN_Display;
    private String ISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrower_return);
        ISBN = "";

        Scann_Button = (Button) findViewById(R.id.Borrrower_return_ISBNbutton);
        ISBN_Display = (TextView) findViewById(R.id.Borrower_return_name);

        Scann_Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(capture_activity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning ISBN");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                ISBN = scanningResult.getContents();
                ISBN_Display.setText("ISBN: " + ISBN);
                send_request();
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
            if (book.getBorrower().equalsIgnoreCase(UserList.getCurrentUser().getUsername())) {
                if (book.getStatus().equalsIgnoreCase(Book.CONFIRM_BORROWED)) {
                    Message msg = new Message(book.getOwner(), book.getBorrower(), ISBN, Book.RETURN, "", "", Message.NOTIFCATION_NOT_SHOWN);
                    book.setStatus(Book.RETURN);
                    MessageList.addMessage(msg);
                    finish();
                }
                else {
                    Toast.makeText(this, "The book is not in the correct status", Toast.LENGTH_SHORT).show();
                }
            }
            else if (book.getOwner().equalsIgnoreCase(UserList.getCurrentUser().getUsername())) {
                if (book.getStatus().equalsIgnoreCase(Book.RETURN)) {
                    ArrayList<Message> msglist = MessageList.searchReceiver(UserList.getCurrentUser().getUsername());
                    for (Message msg : msglist) {
                        if (msg.getISBN().equalsIgnoreCase(book.getISBN())) {
                            MessageList.delete_msg(msg);
                            finish();
                        }
                    }
                    Toast.makeText(this, "There is an error with internal system", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "The book is not in the correct status", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "You are not the current borrower", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "The book is not existed", Toast.LENGTH_SHORT).show();
        }
    }
}