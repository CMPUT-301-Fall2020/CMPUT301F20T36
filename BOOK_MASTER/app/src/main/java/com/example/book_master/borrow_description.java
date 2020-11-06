package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_master.models.*;

public class borrow_description extends AppCompatActivity {
    Book book;
    TextView title, author, isbn;
    Button borrow, back;

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
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getISBN());
        borrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Message m = new Message(UserList.getCurrentUser().getUsername(), book.getAuthor(), book.getISBN(), Book.REQUESTED, "0", "0");
                DBHelper.setMessageDoc(String.valueOf(m.hashCode()), m, borrow_description.this);
                Toast.makeText(borrow_description.this, "Request sent", Toast.LENGTH_SHORT).show();
                book.setStatus(Book.REQUESTED);
                DBHelper.setBookDoc(book.getISBN(), book, borrow_description.this);
                Intent intent = new Intent(borrow_description.this, borrow_list_activity.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(borrow_description.this, borrow_list_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}