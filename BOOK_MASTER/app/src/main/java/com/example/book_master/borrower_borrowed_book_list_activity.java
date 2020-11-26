package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.book_master.adapter.CustomBorrowList;
import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;

import java.util.ArrayList;

public class borrower_borrowed_book_list_activity extends AppCompatActivity {
    private ArrayList<Book> bookData;
    private ArrayAdapter<Book> bookAdapter;
    private ListView bookList;
    private ArrayList<Message> messagelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowed_book_list);


        bookList = findViewById(R.id.borrowed_book_list);

        messagelist = MessageList.searchSender(UserList.getCurrentUser().getUsername());
        bookData = new ArrayList<Book>();

        // check if msg is for requesting and make sure there is data with in
        if (messagelist != null || messagelist.size() > 0) {
            Book temp;
            for (Message msg : messagelist) {
                temp = BookList.getBook(msg.getISBN());
                if (temp == null) {
                    Toast.makeText(borrower_borrowed_book_list_activity.this,
                            "Error from reading message",
                            Toast.LENGTH_SHORT).show();
                }
                else if (msg.getStatus().equalsIgnoreCase(Book.CONFIRM_BORROWED) == false) {
//                    Toast.makeText(borrower_borrowed_book_list_activity.this,
//                            "Book is not in borrowed status",
//                            Toast.LENGTH_SHORT).show();
                }
                else {
                    bookData.add(temp);
                }
            }
        }
        else {
            Toast.makeText(borrower_borrowed_book_list_activity.this,
                    "You do not have any requested book",
                    Toast.LENGTH_SHORT).show();
        }

        bookAdapter = new CustomBorrowList(borrower_borrowed_book_list_activity.this, bookData);
        bookAdapter.notifyDataSetChanged();
        bookList.setAdapter(bookAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(borrower_borrowed_book_list_activity.this, BookInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                bundle.putInt("VISIBILITY", 2);  // 2 for not show Edit button
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}