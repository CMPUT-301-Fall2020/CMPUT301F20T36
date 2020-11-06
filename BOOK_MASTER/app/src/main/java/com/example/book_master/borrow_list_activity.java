package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.CustomBookList;
import com.example.book_master.models.CustomBorrowList;
import com.example.book_master.models.Owner;
import com.example.book_master.models.UserList;

import java.util.ArrayList;

public class borrow_list_activity extends AppCompatActivity {
    ArrayList<Book> bookData;
    ArrayAdapter<Book> bookAdapter;
    ListView bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_list);
        bookList = findViewById(R.id.Borrow_list);
        bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
        bookAdapter = new CustomBorrowList(borrow_list_activity.this, bookData);
        bookList.setAdapter(bookAdapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                bookAdapter.clear();
                bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
                bookAdapter = new CustomBorrowList(borrow_list_activity.this, bookData);
                bookList.setAdapter(bookAdapter);
            }
        });
    }
}