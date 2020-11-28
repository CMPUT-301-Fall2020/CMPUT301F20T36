package com.example.book_master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.book_master.R;
import com.example.book_master.models.BookList;
import com.example.book_master.models.Message;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class CustomMessageList extends ArrayAdapter<Message> {
    private ArrayList<Message> messageData;
    private Context context;

    public CustomMessageList(Context context, ArrayList<Message> messageData){
        super(context, 0, messageData);
        this.context = context;
        this.messageData = messageData;
    }

    @Nullable
    @Override
    public View getView(int position, @androidx.annotation.Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.message_content, parent,false);
        }

        Message message = messageData.get(position);
        TextView sender = view.findViewById(R.id.message_content_sender);
        TextView description = view.findViewById(R.id.message_content_description);
        TextView title = view.findViewById(R.id.message_content_book);

        title.setText(BookList.getBook(message.getISBN()).getTitle());
        sender.setText(message.getSender());
        description.setText(message.getStatus());

        return view;
    }
}
