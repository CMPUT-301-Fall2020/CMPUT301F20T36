package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.DBHelper;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.google.android.gms.maps.SupportMapFragment;

public class request_description extends AppCompatActivity {
    private TextView title, status, sender, receiver;
    private Button accept, decline, back, show_map;
    private Message message;
    private String s, m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_description);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        message = (Message) bundle.getSerializable("message");
        s = (String) bundle.getSerializable("status");
        m = (String) bundle.getSerializable("mode");

        title = findViewById(R.id.Request_BookTitle);
        status = findViewById(R.id.Request_BookStatus);
        sender = findViewById(R.id.Request_BookSender);
        accept = findViewById(R.id.Request_ButtonAccept);
        decline = findViewById(R.id.Request_ButtonDecline);
        back = findViewById(R.id.Request_ButtonBack);
        receiver = findViewById(R.id.Request_BookReceiver);
        show_map = findViewById(R.id.Request_ButtonShowMap);
        if(BookList.getBook(message.getISBN()) != null) {
            title.setText(BookList.getBook(message.getISBN()).getTitle());
        }

        status.setText(message.getStatus());
        sender.setText(message.getSender());
        receiver.setText(message.getReceiver());

        accept.setVisibility(View.GONE);
        decline.setVisibility(View.GONE);
        show_map.setVisibility(View.GONE);

        if(m.equals("RECEIVED") && s.equals(Book.REQUESTED)) { // owner recieve a request
            accept.setVisibility(View.VISIBLE);
            decline.setVisibility(View.VISIBLE);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String isbn = message.getISBN();
                    Book b = BookList.getBook(isbn);

                    DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), request_description.this);

                    for(Message i : MessageList.searchISBN(isbn)){
                        if(i.getStatus().equals(Book.REQUESTED)){
                            DBHelper.deleteMessageDoc(String.valueOf(i.hashCode()), request_description.this);
                        }
                    }

                    message.setStatus(Book.ACCEPTED);
                    message.setReceiver(message.getSender());
                    message.setSender(b.getOwner());
                    DBHelper.setMessageDoc(String.valueOf(message.hashCode()), message,request_description.this);

                    b.setStatus(Book.ACCEPTED);
                    b.setBorrower(message.getSender());
                    DBHelper.setBookDoc(isbn,b,request_description.this);

                    Intent intent = new Intent(request_description.this, map_select_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Message", message);
                    bundle.putInt("Visibility", 1);
                    bundle.putDouble("Latitude", map_select_activity.EDMONTON_LATITUDE);
                    bundle.putDouble("Longitude", map_select_activity.EDMONTON_LONGITUDE);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            });
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String isbn = message.getISBN();
                    Book b = BookList.getBook(isbn);

                    DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), request_description.this);

                    b.setStatus(Book.AVAILABLE);
                    for(Message i : MessageList.searchISBN(isbn)){
                        if(i.getStatus().equals(Book.REQUESTED)){
                            b.setStatus(Book.REQUESTED);
                            break;
                        }
                    }
                    DBHelper.setBookDoc(isbn, b, request_description.this);

                    Intent intent = new Intent(request_description.this, request_navigator.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (m.equals("SENT") && s.equals(Book.ACCEPTED)) {
            show_map.setVisibility(View.VISIBLE);
            accept.setVisibility(View.VISIBLE);
            accept.setText("Hand Over");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent borrower_return_intent = new Intent(request_description.this, borrower_return_activity.class);
                    startActivity(borrower_return_intent);
                }
            });
            show_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent map_view_intent = new Intent(request_description.this, map_select_activity.class);
                    Bundle map_view_bundle = new Bundle();
                    map_view_bundle.putSerializable("Message", message);
                    map_view_bundle.putInt("Visibility", 2);
                    map_view_bundle.putDouble("Latitude", Double.valueOf(message.getLatitude()));
                    map_view_bundle.putDouble("Longitude", Double.valueOf(message.getLongitude()));
                    map_view_intent.putExtras(map_view_bundle);
                    startActivity(map_view_intent);
                }
            });
        } else if (m.equals("RECEIVED") && s.equals(Book.ACCEPTED)) {
            show_map.setVisibility(View.VISIBLE);
            show_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent map_view_intent = new Intent(request_description.this, map_select_activity.class);
                    Bundle map_view_bundle = new Bundle();
                    map_view_bundle.putSerializable("Message", message);
                    map_view_bundle.putInt("Visibility", 2);
                    map_view_bundle.putDouble("Latitude", Double.valueOf(message.getLatitude()));
                    map_view_bundle.putDouble("Longitude", Double.valueOf(message.getLongitude()));
                    map_view_intent.putExtras(map_view_bundle);
                    startActivity(map_view_intent);
                }
            });
        } else if (m.equals("SENT") && s.equals(Book.BORROWED)) {
            accept.setVisibility(View.VISIBLE);
            accept.setText("Hand Over");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent borrower_return_intent = new Intent(request_description.this, borrower_return_activity.class);
                    startActivity(borrower_return_intent);
                }
            });
        } else if (m.equals("RECEIVED") && s.equals(Book.BORROWED)) {
            accept.setVisibility(View.VISIBLE);
            accept.setText("Hand Over");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent borrower_return_intent = new Intent(request_description.this, borrower_return_activity.class);
                    startActivity(borrower_return_intent);
                }
            });
        } else if (m.equals("SENT") && s.equals(Book.CONFIRM_BORROWED)) {
            accept.setVisibility(View.VISIBLE);
            accept.setText("Hand Over");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent borrower_return_intent = new Intent(request_description.this, borrower_return_activity.class);
                    startActivity(borrower_return_intent);
                }
            });
        } else if (m.equals("RECEIVED") && s.equals(Book.CONFIRM_BORROWED)) {
            accept.setVisibility(View.VISIBLE);
            accept.setText("Hand Over");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent borrower_return_intent = new Intent(request_description.this, borrower_return_activity.class);
                    startActivity(borrower_return_intent);
                }
            });
        } else if (m.equals("SENT") && s.equals(Book.RETURN)) {
            accept.setVisibility(View.VISIBLE);
            accept.setText("Hand Over");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent borrower_return_intent = new Intent(request_description.this, borrower_return_activity.class);
                    startActivity(borrower_return_intent);
                }
            });
        } else if (m.equals("RECEIVED") && s.equals(Book.RETURN)) {
            accept.setVisibility(View.VISIBLE);
            accept.setText("Hand Over");
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent borrower_return_intent = new Intent(request_description.this, borrower_return_activity.class);
                    startActivity(borrower_return_intent);
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(request_description.this, request_navigator.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}
