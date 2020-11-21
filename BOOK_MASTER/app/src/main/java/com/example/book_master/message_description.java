package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.book_master.models.BookList;
import com.example.book_master.models.Message;


public class message_description extends AppCompatActivity {
    private Message message;
    private TextView title, sender, reciver, status, location;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_description);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        message = (Message) bundle.getSerializable("message");

        title = (TextView) findViewById(R.id.message_description_title);
        sender = (TextView) findViewById(R.id.message_description_sender);
        reciver = (TextView) findViewById(R.id.message_description_reciever);
        status = (TextView) findViewById(R.id.message_description_status);
        location = (TextView) findViewById(R.id.message_description_location);
        back = (Button) findViewById(R.id.message_description_back);

        title.setText(BookList.getBook(message.getISBN()).getTitle());
        sender.setText(message.getSender());
        reciver.setText(message.getReceiver());
        status.setText(message.getStatus());

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}