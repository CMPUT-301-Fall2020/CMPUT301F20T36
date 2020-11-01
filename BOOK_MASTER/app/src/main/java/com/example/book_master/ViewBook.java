package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.book_master.models.*;

import java.util.ArrayList;

public class ViewBook extends AppCompatActivity {
    ListView bookList;
    ArrayList<Book> bookData;
    ArrayAdapter<Book> bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView top = findViewById(R.id.top);
        bookList = findViewById(R.id.books);
        top.setTextColor(android.graphics.Color.BLUE);
        setContentView(R.layout.activity_view_book);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Owner owner =(Owner) bundle.getSerializable("owner");
        bookData = owner.Get_Owned_Books();
        bookAdapter = new CustomBookList(this, bookData);
        bookList.setAdapter(bookAdapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(ViewBook.this, BookInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}