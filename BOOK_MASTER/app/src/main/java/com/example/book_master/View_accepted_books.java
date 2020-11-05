package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.book_master.models.*;

import java.util.*;

public class View_accepted_books extends AppCompatActivity {
    ListView bookList;
    ArrayList<Book> bookData;
    ArrayAdapter<Book> bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accepted_books);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bookData = (ArrayList<Book>) bundle.getSerializable("accepted_books");

        bookAdapter = new CustomBookList(this, bookData);
        bookList.findViewById(R.id.accepted_book);
        bookList.setAdapter(bookAdapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(View_accepted_books.this, BookInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}