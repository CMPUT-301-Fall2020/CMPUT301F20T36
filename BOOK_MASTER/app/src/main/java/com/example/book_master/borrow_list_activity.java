package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.book_master.models.Book;
import com.example.book_master.models.CustomBookList;
import com.example.book_master.models.Owner;

import java.util.ArrayList;

public class borrow_list_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_list);

    }
}