package com.example.book_master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book_master.R;
import com.example.book_master.models.BookList;
import com.example.book_master.models.Message;

import java.util.ArrayList;

public class CustomRequestList extends ArrayAdapter<Message> {
    private ArrayList<Message> messData;
    private Context context;

    public CustomRequestList(Context context, ArrayList<Message> messData){
        super(context, 0, messData);
        this.context = context;
        this.messData = messData;
    }

    @Nullable
    @Override
    public View getView(int position, @androidx.annotation.Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.request_list_content, parent,false);
        }
        Message message = messData.get(position);
        TextView title = view.findViewById(R.id.Request_list_bookname);
        TextView sender = view.findViewById(R.id.Request_list_sender);
        TextView status = view.findViewById(R.id.Request_list_status);

        if(BookList.getBook(message.getISBN()) != null){
            title.setText(BookList.getBook(message.getISBN()).getTitle());
        }

        sender.setText(message.getSender());
        status.setText(message.getStatus());

        return view;
    }
}
