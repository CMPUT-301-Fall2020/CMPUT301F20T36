package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

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

public class request_description extends AppCompatActivity {
    TextView title, status, sender;
    Button accept, decline, back;
    Message message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_description);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        message = (Message) bundle.getSerializable("message");
        title = findViewById(R.id.Request_BookTitle);
        status = findViewById(R.id.Request_BookStatus);
        sender = findViewById(R.id.Request_BookSender);
        accept = findViewById(R.id.Request_ButtonAccept);
        decline = findViewById(R.id.Request_ButtonDecline);
        back = findViewById(R.id.Request_ButtonBack);
        if(BookList.getBook(message.getISBN()) != null) {
            title.setText(BookList.getBook(message.getISBN()).getTitle());
        }
        status.setText(message.getStatus());
        sender.setText(message.getSender());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), request_description.this);
                message.setStatus(Book.ACCEPTED);
                DBHelper.setMessageDoc(String.valueOf(message.hashCode()), message,request_description.this);
                String isbn = message.getISBN();
                Book b = BookList.getBook(isbn);
                b.setStatus(Book.ACCEPTED);
                DBHelper.setBookDoc(isbn,b,request_description.this);
                Intent intent = new Intent(request_description.this, request_list.class);
                startActivity(intent);
                finish();
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), request_description.this);
                String isbn = message.getISBN();
                Book b = BookList.getBook(isbn);
                b.setStatus(Book.AVAILABLE);
                for(Message i : MessageList.searchISBN(isbn)){
                    if(i.getStatus().equals(Book.REQUESTED)){
                        b.setStatus(Book.REQUESTED);
                        break;
                    }
                }
                DBHelper.setBookDoc(isbn, b, request_description.this);
                Intent intent = new Intent(request_description.this, request_list.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(request_description.this, request_list.class);
                startActivity(intent);
                finish();
            }
        });
    }
}