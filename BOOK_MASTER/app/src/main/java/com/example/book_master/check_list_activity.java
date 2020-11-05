package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.CustomBookList;
import com.example.book_master.models.Owner;
import com.example.book_master.models.User;
import com.example.book_master.models.UserList;

import java.util.*;

public class check_list_activity extends AppCompatActivity {
    Button add_button;
    ListView bookList;
    ArrayList<Book> bookData;
    ArrayAdapter<Book> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_my_list);
        add_button = findViewById(R.id.check_list_add);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent check_list_intent = new Intent(check_list_activity.this, add_book_activity.class);
                startActivity(check_list_intent);
            }
        });

        bookList = findViewById(R.id.books);

        User cur = UserList.getCurrentUser();
        String name = cur.getUsername();
        bookData = BookList.getOwnedBook(name);
        bookAdapter = new CustomBookList(this, bookData);
        bookList.setAdapter(bookAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(check_list_activity.this, BookInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}


