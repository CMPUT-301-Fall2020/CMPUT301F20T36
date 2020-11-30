package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.*;

import com.example.book_master.adapter.CustomImageList;
import com.example.book_master.fragment.check_list;
import com.example.book_master.models.*;

/**
 * US 01.06.01
 * As an owner, I want to view and edit a book description in my books.
 * Show the book information if it is accessed by the owner.
 * The user then could edit the book (checked by VISIBILITY input within the bundle).
 */
public class book_description_activity extends AppCompatActivity {
    private TextView BookTitle, BookAuthor, BookISBN, BookStatus, CurrentBorrower;
    private Button Edit, Delete;
    private Book book;
    private int visibility;

    private ArrayList<Image> imageList;
    private CustomImageList imageAdapter;
    private RecyclerView recyclerView;

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
        CurrentBorrower.setText("Current Borrower: " + book.getBorrower());

        // retrieve images being bundles to the current book from Firebase Storage
        // and display them in recyclerView
        imageList = new ArrayList<Image>();
        imageAdapter = new CustomImageList(imageList);
        recyclerView = (RecyclerView) findViewById(R.id.book_description_imagineRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(imageAdapter);
        DBHelper.retrieveImagine(imageList, imageAdapter, book.getISBN(), this);

        // check if the user is owner or borrower
        if (visibility == 2) {
            Edit.setVisibility(View.INVISIBLE);
        }
        else {
            Edit.setVisibility(View.VISIBLE);
        }

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(book_description_activity.this, edit_book_activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book_edit", book);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /**
         * US 01.07.01
         * As an owner, I want to delete a book in my books.
         */
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserList.getCurrentUser().Remove_Owned_Book(book.getISBN(), book_description_activity.this);
//                Intent intent = new Intent(book_description_activity.this, check_list.class);
//                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        BookTitle.setText("Title: " + book.getTitle());
        BookAuthor.setText("Author: " + book.getAuthor());
        BookISBN.setText("ISBN: " + book.getISBN());
        BookStatus.setText("Status: " + book.getStatus());
        CurrentBorrower.setText("Current Borrower: " + book.getBorrower());

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(imageAdapter);
        DBHelper.retrieveImagine(imageList, imageAdapter, book.getISBN(), this);
    }
}
