package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_master.adapter.CustomBorrowList;
import com.example.book_master.adapter.CustomMessageList;
import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;

import java.util.ArrayList;

public class show_notification_activity extends AppCompatActivity {
    private ArrayList<Message> messageData;
    private ArrayAdapter<Message> messageAdapter;
    private ListView messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_message_list);

        messageList = (ListView) findViewById(R.id.show_message_list_listview);

        messageData = MessageList.searchReceiver(UserList.getCurrentUser().getUsername());

        messageAdapter = new CustomMessageList(show_notification_activity.this, messageData);
        messageAdapter.notifyDataSetChanged();
        messageList.setAdapter(messageAdapter);

//        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//                Intent intent = new Intent(show_notification_activity.this, message_description.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("message", messageData.get(position));
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 3);
////                finish();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 3 || resultCode == RESULT_OK) {
            messageAdapter.clear();
            messageData = MessageList.searchReceiver(UserList.getCurrentUser().getUsername());

            messageAdapter = new CustomMessageList(show_notification_activity.this, messageData);
            messageAdapter.notifyDataSetChanged();
            messageList.setAdapter(messageAdapter);
        }
    }
}
