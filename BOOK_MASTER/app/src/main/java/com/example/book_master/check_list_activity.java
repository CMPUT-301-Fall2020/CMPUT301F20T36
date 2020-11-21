package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.adapter.CustomBookList;
import com.example.book_master.models.UserList;

import java.util.*;

/**
 * this activity class will show the owner what he owned (can be filtered by status)
 */
public class check_list_activity extends AppCompatActivity {
    private Button add_button;
    private ListView bookList;
    private ArrayList<Book> bookData;
    private ArrayAdapter<Book> bookAdapter;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;

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
                finish();
            }
        });

        bookList = findViewById(R.id.books);
        final String name = UserList.getCurrentUser().getUsername();
        bookData = BookList.getOwnedBook(name);
        bookAdapter = new CustomBookList(this, bookData);
        bookList.setAdapter(bookAdapter);

        // if user click on a book, then display the entire dscription
        // if user click on a book, then display the entire description
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(check_list_activity.this, BookInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                bundle.putInt("VISIBILITY", 1);  // 1 for show Edit button
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        // Owner can filtered book by status which is handled by spinner
        spinner = findViewById(R.id.status_spinner);
        final String[] status = {"All", Book.AVAILABLE, Book.REQUESTED, Book.ACCEPTED, Book.BORROWED, Book.CONFIRM_BORROWED, Book.CONFIRM_RETURN};
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                bookAdapter.clear();
                if(position == 0){
                    bookData = BookList.getOwnedBook(name);
                }else{
                    ArrayList<Book> ownedBook = BookList.getOwnedBook(name);
                    bookData = UserList.getCurrentUser().Get_Owned_Books(status[position]);
                }
                bookAdapter = new CustomBookList(check_list_activity.this, bookData);
                bookList.setAdapter(bookAdapter);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
    }
}


