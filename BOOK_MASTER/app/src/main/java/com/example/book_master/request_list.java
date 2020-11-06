package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.book_master.models.*;
import com.example.book_master.Adpater.*;
import java.util.ArrayList;

public class request_list extends AppCompatActivity {

    ListView requestList;
    ArrayList<Message> messData = new ArrayList<Message>();
    ArrayAdapter<Message> messAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_list);
        requestList = findViewById(R.id.Request_list);
        final String name = UserList.getCurrentUser().getUsername();
        for(Message i : MessageList.searchReceiver(name)){
            if(i.getStatus().equals("REQUESTED")){
                messData.add(i);
            }
        }
        messAdapter = new CustomRequestList(this, messData);
        requestList.setAdapter(messAdapter);
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(request_list.this, request_description.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", messData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}