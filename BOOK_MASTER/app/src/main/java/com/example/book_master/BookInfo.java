package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.util.*;
import com.example.book_master.models.*;

public class BookInfo extends AppCompatActivity {
    TextView BookTitle, BookAuthor, BookISBN, BookStatus, CurrentBorrower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Book book = (Book) bundle.getSerializable("book");
        BookTitle = findViewById(R.id.BookTitle);
        BookAuthor = findViewById(R.id.BookAuthor);
        BookISBN = findViewById(R.id.BookISBN);
        BookStatus = findViewById(R.id.BookStatus);
        CurrentBorrower = findViewById(R.id.CurrentBorrower);
        BookTitle.setText(book.getTitle());
        BookAuthor.setText(book.getAuthor());
        BookISBN.setText(book.getISBN());
        BookStatus.setText(book.getStatus());
        CurrentBorrower.setText(book.getHolder().getUserName());
    }
}