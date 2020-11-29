package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_master.adapter.CustomImageList;
import com.example.book_master.models.*;

import java.util.ArrayList;

/**
 * US 03.02.01
 * As a borrower, I want search results to show each book with its description, owner username, and status.
 * This activity page will display all description to user and ask user if he want to borrow it
 * User can click on Borrow to send a request to the owner
 */
public class search_page_book_description extends AppCompatActivity {
    private Book book;
    private TextView title, author, isbn, status, owner;
    private Button borrow, back;

    private ArrayList<Image> imageList;
    private CustomImageList imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_description);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        book = (Book) bundle.getSerializable("book");

        title = findViewById(R.id.Borrow_BookTitle);
        author = findViewById(R.id.Borrow_BookAuthor);
        isbn = findViewById(R.id.Borrow_BookISBN);
        borrow = findViewById(R.id.Borrow_borrow_button);
        back = findViewById(R.id.Borrow_back_button);
        status = findViewById(R.id.Borrow_BookStatus);
        owner = findViewById(R.id.Borrow_BookOwner);

        title.setText("Title: " + book.getTitle());
        author.setText("Author: " + book.getAuthor());
        isbn.setText("ISBN: " + book.getISBN());
        status.setText("States: " + book.getStatus());
        owner.setText("Owner: " + book.getOwner());
        borrow.setText("Request");

        /**
         * US 08.03.01
         * As an owner or borrower, I want to view any attached photograph for a book.
         */
        // retrieve images being bundles to the current book from Firebase Storage
        // and display them in recyclerView
        imageList = new ArrayList<>();
        imageAdapter = new CustomImageList(imageList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.borrow_description_imagineRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(imageAdapter);
        DBHelper.retrieveImagine(imageList, imageAdapter, book.getISBN(), this);

        // will send the request to owner for requesting the book
        borrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Message m = new Message(UserList.getCurrentUser().getUsername(), book.getOwner(), book.getISBN(), Book.REQUESTED, "0", "0");
                DBHelper.setMessageDoc(String.valueOf(m.hashCode()), m, search_page_book_description.this);
                Toast.makeText(search_page_book_description.this, "Request sent", Toast.LENGTH_SHORT).show();
                book.setStatus(Book.REQUESTED);
                DBHelper.setBookDoc(book.getISBN(), book, search_page_book_description.this);
                setResult(RESULT_OK);
                finish();
            }
        });

        // back to main menu.
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(search_description.this, borrower_requested_list_activity.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}