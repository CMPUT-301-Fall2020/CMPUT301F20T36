package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_master.models.*;

/**
 * This activity page will dispay all description to user and ask user if he want to borrow it
 * User can click on Borrow to send a request to the owner
 */
public class search_description extends AppCompatActivity {
    private Book book;
    private TextView title, author, isbn, status, owner;
    private Button borrow, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_description);

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

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN());
        status.setText(book.getStatus());
        owner.setText(book.getOwner());

        // will send the request to owner for requesting the book
        borrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Message m = new Message(UserList.getCurrentUser().getUsername(), book.getOwner(), book.getISBN(), Book.REQUESTED, "0", "0");
                DBHelper.setMessageDoc(String.valueOf(m.hashCode()), m, search_description.this);
                Toast.makeText(search_description.this, "Request sent", Toast.LENGTH_SHORT).show();
                book.setStatus(Book.REQUESTED);
                DBHelper.setBookDoc(book.getISBN(), book, search_description.this);
                Intent intent = new Intent(search_description.this, borrower_requested_list_activity.class);
                startActivity(intent);
                finish();
            }
        });

        // back to main menu.
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(search_description.this, borrower_requested_list_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}