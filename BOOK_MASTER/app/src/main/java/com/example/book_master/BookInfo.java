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
    TextView BookTitle, BookAuthor, BookISBN, BookStatus, CurrentBorrower;
    Button Edit, Delete;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_description);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        book = (Book) bundle.getSerializable("book");

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

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(BookInfo.this, edit_book_activity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("book_edit", book);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BookList.delete(book);
                DBHelper.deleteBookDoc(book.getISBN(), BookInfo.this);
            }
        });
    }
}