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
import android.widget.TextView;

import com.example.book_master.adapter.CustomRequestList;
import com.example.book_master.models.Book;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;

import java.util.ArrayList;

public class request_menu extends AppCompatActivity {
    Button sent, received;
    ListView requestList;
    TextView requestListTitle;
    ArrayList<Message> messData = new ArrayList<Message>();
    ArrayAdapter<Message> messAdapter;
    Spinner spinner_status, spinner_mode;
    ArrayAdapter<String> spinnerAdapter_status, spinnerAdapter_mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_list);
        requestList = findViewById(R.id.Request_list);
        requestListTitle = findViewById(R.id.Request_list_title);
        spinner_status = findViewById(R.id.request_status_spinner);
        spinner_mode = findViewById(R.id.request_mode_spinner);
        requestListTitle.setText("           Request List");
        final String name = UserList.getCurrentUser().getUsername();
        final String[] status = {"All", Book.REQUESTED, Book.ACCEPTED, Book.BORROWED};
        final String[] mode = {"SENT", "RECEIVED"};
        messData = MessageList.searchSender(name);
        messAdapter = new CustomRequestList(this, messData);
        requestList.setAdapter(messAdapter);
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(request_menu.this, request_description.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message", messData.get(position));
                bundle.putSerializable("status", spinner_status.getSelectedItem().toString());
                bundle.putSerializable("mode", spinner_mode.getSelectedItem().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        spinnerAdapter_status = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);
        spinnerAdapter_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(spinnerAdapter_status);

        spinnerAdapter_mode = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mode);
        spinnerAdapter_mode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mode.setAdapter(spinnerAdapter_mode);

        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                messAdapter.clear();
                if(position == 0) {
                    if(spinner_mode.getSelectedItemPosition() == 0) {
                        messData = MessageList.searchSender(name);
                    }else{
                        messData = MessageList.searchReceiver(name);
                    }
                } else {
                    ArrayList<Message> temp = new ArrayList<Message>();
                    if(spinner_mode.getSelectedItemPosition() == 0) {
                        for (Message i : MessageList.searchSender(name)) {
                            if (i.getStatus().equals(status[position])) {
                                temp.add(i);
                            }
                        }
                    }else{
                        for (Message i : MessageList.searchReceiver(name)) {
                            if (i.getStatus().equals(status[position])) {
                                temp.add(i);
                            }
                        }
                    }
                    messData = temp;
                }
                messAdapter = new CustomRequestList(request_menu.this, messData);
                requestList.setAdapter(messAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                messAdapter.clear();
                if(spinner_status.getSelectedItemPosition() == 0){
                    if(position == 0){
                        messData = MessageList.searchSender(name);
                    }else{
                        messData = MessageList.searchReceiver(name);
                    }
                }else{
                    ArrayList<Message> temp = new ArrayList<Message>();
                    if(position == 0){
                        for(Message i : MessageList.searchSender(name)){
                            if(i.getStatus().equals(spinner_status.getSelectedItem().toString())){
                                temp.add(i);
                            }
                        }
                    }else{
                        for(Message i : MessageList.searchReceiver(name)){
                            if(i.getStatus().equals(spinner_status.getSelectedItem().toString())){
                                temp.add(i);
                            }
                        }
                    }
                    messData = temp;
                }
                messAdapter = new CustomRequestList(request_menu.this, messData);
                requestList.setAdapter(messAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}