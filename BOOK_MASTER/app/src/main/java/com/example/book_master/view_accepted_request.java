package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.book_master.adapter.CustomBookList;
import com.example.book_master.adapter.CustomRequestList;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class view_accepted_request extends AppCompatActivity {
    ListView requestList;
    TextView requestListTitle;
    ArrayList<Message> messData = new ArrayList<Message>();
    ArrayAdapter<Message> messAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_list);
        requestList = findViewById(R.id.Request_list);
        requestListTitle = findViewById(R.id.Request_list_title);
        requestListTitle.setText("       Accepted Request List");
        final String name = UserList.getCurrentUser().getUsername();
        for(Message i : MessageList.searchSender(name)){
            if(i.getStatus().equals("ACCEPTED")){
                messData.add(i);
            }
        }
        messAdapter = new CustomRequestList(this, messData);
        requestList.setAdapter(messAdapter);

    }
}