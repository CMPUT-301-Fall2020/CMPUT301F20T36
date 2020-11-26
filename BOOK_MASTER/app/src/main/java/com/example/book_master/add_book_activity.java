package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.DBHelper;
import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Random;

public class add_book_activity extends AppCompatActivity {
    private EditText Title;
    private EditText Author;
    private Button ScanISBN;
    private Button Confirm;
    private Button Discard;
    private String ISBN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_book);

        Title = (EditText) findViewById(R.id.add_book_name);
        Author = (EditText) findViewById(R.id.add_book_author);
        ScanISBN = (Button) findViewById(R.id.add_edit_book_scan_isbn);
        Confirm = (Button) findViewById(R.id.add_edit_book_confirm);
        Discard = (Button) findViewById(R.id.add_edit_book_discard);
        ISBN = "";
        // ------For testing------
        Random rand = new Random();
        ISBN = Double.toString(rand.nextDouble());

        ScanISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(add_book_activity.this);
                integrator.setCaptureActivity(capture_activity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning ISBN");
                integrator.initiateScan();
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book_title = Title.getText().toString();
                String book_Author = Author.getText().toString();
                Book temp =  BookList.getBook(ISBN);
                if (book_Author != "" && book_title != "" && ISBN != ""  && temp == null) {
                    Book book = new Book(book_title, book_Author, ISBN);
                    UserList.getCurrentUser().Add_Book_Owned(book, add_book_activity.this);

                    Intent intent = new Intent(add_book_activity.this, check_list_activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(add_book_activity.this, "Field is not valid.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_book_activity.this, check_list_activity.class);
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
                ISBN = scanningResult.getContents();
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}