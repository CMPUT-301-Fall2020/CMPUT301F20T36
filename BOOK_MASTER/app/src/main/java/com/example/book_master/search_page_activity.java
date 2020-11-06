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

import com.example.book_master.Adpater.CustomBookList;
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
        bookAdapter = new CustomBorrowList(search_page_activity.this, bookData);
        bookList.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(search_page_activity.this, search_description.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        scan_ISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(search_page_activity.this);
                integrator.setCaptureActivity(capture_activity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning ISBN");
                integrator.initiateScan();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = keyword.getText().toString();

                bookAdapter.clear();
                if (text == null) {
                    Toast.makeText(search_page_activity.this, "The input is null", Toast.LENGTH_SHORT).show();
                }
                else if (text.equalsIgnoreCase("")) {
                    bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
                    Toast.makeText(search_page_activity.this, "Show all Book", Toast.LENGTH_SHORT).show();
                }
                else {
                    bookData = BookList.searchDesc(text, UserList.getCurrentUser().getUsername());
                }
                bookAdapter = new CustomBorrowList(search_page_activity.this, bookData);
                bookList.setAdapter(bookAdapter);
                bookAdapter.notifyDataSetChanged();
            }
        });
    }

//    public void Search() {
////        String text = keyword.getText().toString();
////        if (text == null) {
////            Toast.makeText(search_page_activity.this, "The input is null", Toast.LENGTH_SHORT).show();
////        }
////        else if (text == "") {
////            bookData = bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
////        }
////        else {
////            bookData = BookList.searchDesc(text);
////        }
////        bookAdapter.notifyDataSetChanged();
////    }

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