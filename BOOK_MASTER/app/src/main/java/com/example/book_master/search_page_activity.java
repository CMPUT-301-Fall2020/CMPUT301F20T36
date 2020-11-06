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
import android.widget.Toast;

import com.example.book_master.Adpater.CustomBorrowList;
import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class search_page_activity extends AppCompatActivity {
    private Button scan_ISBN;
    private Button search;
    private TextView keyword;
    private String ISBN;
    private ArrayList<Book> bookData;
    private ArrayAdapter<Book> bookAdapter;
    private ListView bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        bookList = (ListView) findViewById(R.id.search_page_booklist);
        scan_ISBN = (Button) findViewById(R.id.search_bar_ISBN);
        search = (Button) findViewById(R.id.search_bar_confirm);
        keyword = (TextView) findViewById(R.id.search_bar_keyword);

        ISBN = "";
        bookData = bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
        bookAdapter = new CustomBorrowList(search_page_activity.this, bookData);bookList.setAdapter(bookAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(search_page_activity.this, BookInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                String ISBN = scanningResult.getContents();
                keyword.setText(ISBN);
            }
            else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}