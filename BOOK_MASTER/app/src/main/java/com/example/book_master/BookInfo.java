package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;
import com.example.book_master.models.*;

public class BookInfo extends AppCompatActivity {
    private TextView BookTitle, BookAuthor, BookISBN, BookStatus, CurrentBorrower;
    private Button Edit, Delete;
    private Book book;
    private int visibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_description);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        book = (Book) bundle.getSerializable("book");
        visibility = (int) bundle.getInt("VISIBILITY");

        BookTitle = findViewById(R.id.BookTitle);
        BookAuthor = findViewById(R.id.BookAuthor);
        BookISBN = findViewById(R.id.BookISBN);
        BookStatus = findViewById(R.id.BookStatus);
        CurrentBorrower = findViewById(R.id.Book_Borrower);
        Edit = (Button) findViewById(R.id.Edit_book_desc);
        Delete = (Button) findViewById(R.id.add_delete_button);

        BookTitle.setText("Title: " + book.getTitle());
        BookAuthor.setText("Author: " + book.getAuthor());
        BookISBN.setText("ISBN: " + book.getISBN());
        BookStatus.setText("Status: " + book.getStatus());
        CurrentBorrower.setText("Currnet Borrower: " + book.getBorrower());

        if (visibility == 2) {
            Edit.setVisibility(View.INVISIBLE);
        }
        else {
            Edit.setVisibility(View.VISIBLE);
        }

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BookInfo.this, edit_book_activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book_edit", book);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserList.getCurrentUser().Remove_Owned_Book(book.getISBN(), BookInfo.this);
                Intent intent = new Intent(BookInfo.this, check_list_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}