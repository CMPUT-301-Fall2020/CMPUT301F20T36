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
 * This activity page will display all description to user and ask user if he want to borrow it
 * User can click on Borrow to send a request to the owner
 */
// As a borrower,
// I want search results to show each book with its description, owner username, and status.
public class search_page_book_description extends AppCompatActivity {
    private Book book;
    private TextView title, author, isbn, status, owner, owner_description;
    private Button borrow, back;

    private ArrayList<Image> imageList;
    private CustomImageList imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_description);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        book = (Book) bundle.getSerializable("book");

        title = findViewById(R.id.BookTitle);
        author = findViewById(R.id.BookAuthor);
        isbn = findViewById(R.id.BookISBN);
        borrow = findViewById(R.id.Edit_book_desc);
        back = findViewById(R.id.add_delete_button);
        status = findViewById(R.id.BookStatus);
        owner = findViewById(R.id.Book_Borrower);
        owner_description = findViewById(R.id.Book_borrower_description);

        borrow.setText("Request");
        back.setText("Back");
        owner_description.setText("Owner");
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN());
        status.setText(book.getStatus());
        owner.setText(book.getOwner());

        // As an owner or borrower, I want to view any attached photograph for a book.
        // retrieve images being bundles to the current book from Firebase Storage
        // and display them in recyclerView
        imageList = new ArrayList<>();
        imageAdapter = new CustomImageList(imageList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.book_description_imagineRecyclerView);
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
                finish();
            }
        });
    }
}
