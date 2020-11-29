package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.DBHelper;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.google.android.gms.maps.SupportMapFragment;

public class request_description extends AppCompatActivity {
    TextView title, status, sender, receiver;
    Button accept, decline, back;
    Message message;
    String s, m;
    SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_description);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        message = (Message) bundle.getSerializable("message");
        s = (String) bundle.getSerializable("status");
        m = (String) bundle.getSerializable("mode");

        title = findViewById(R.id.Request_BookTitle);
        status = findViewById(R.id.Request_BookStatus);
        sender = findViewById(R.id.Request_BookSender);
        accept = findViewById(R.id.Request_ButtonAccept);
        decline = findViewById(R.id.Request_ButtonDecline);
        back = findViewById(R.id.Request_ButtonBack);
        receiver = findViewById(R.id.Request_BookReceiver);
        if(BookList.getBook(message.getISBN()) != null) {
            title.setText(BookList.getBook(message.getISBN()).getTitle());
        }

        status.setText(message.getStatus());
        sender.setText(message.getSender());
        receiver.setText(message.getReceiver());

        accept.setVisibility(View.GONE);
        decline.setVisibility(View.GONE);

        if(m.equals("RECEIVED") && s.equals(Book.REQUESTED)){
            accept.setVisibility(View.VISIBLE);
            decline.setVisibility(View.VISIBLE);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), request_description.this);
                    message.setStatus(Book.ACCEPTED);
                    DBHelper.setMessageDoc(String.valueOf(message.hashCode()), message,request_description.this);
                    String isbn = message.getISBN();
                    Book b = BookList.getBook(isbn);
                    b.setStatus(Book.ACCEPTED);
                    b.setBorrower(message.getSender());
                    DBHelper.setBookDoc(isbn,b,request_description.this);
                    Intent intent = new Intent(request_description.this, request_navigator.class);
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
                    Intent intent = new Intent(request_description.this, request_navigator.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (m.equals("SENT") && s.equals(Book.ACCEPTED)) {
            accept.setVisibility(View.VISIBLE);
            decline.setVisibility(View.VISIBLE);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), request_description.this);
                    message.setStatus(Book.BORROWED);
                    DBHelper.setMessageDoc(String.valueOf(message.hashCode()), message,request_description.this);
                    String isbn = message.getISBN();
                    Book b = BookList.getBook(isbn);
                    b.setStatus(Book.BORROWED);
                    DBHelper.setBookDoc(isbn,b,request_description.this);
                    Intent intent = new Intent(request_description.this, request_navigator.class);
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
                    Intent intent = new Intent(request_description.this, request_navigator.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (m.equals("RECEIVED") && s.equals(Book.RETURN)) {
            accept.setVisibility(View.VISIBLE);
            accept.setText("CONFIRM");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), request_description.this);
                    String isbn = message.getISBN();
                    Book b = BookList.getBook(isbn);
                    b.setStatus(Book.AVAILABLE);
                    DBHelper.setBookDoc(isbn,b,request_description.this);
                    Intent intent = new Intent(request_description.this, request_navigator.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(request_description.this, request_navigator.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
