package com.example.book_master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.book_master.R;
import com.example.book_master.models.Book;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class CustomBookList extends ArrayAdapter<Book> {
    private ArrayList<Book> bookData;
    private Context context;
    public CustomBookList(Context context, ArrayList<Book> bookData){
        super(context, 0, bookData);
        this.context = context;
        this.bookData = bookData;
    }
    @Nullable
    @Override
    public View getView(int position, @androidx.annotation.Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.book_content, parent,false);
        }
        Book book = bookData.get(position);
        TextView title = view.findViewById(R.id.book_contentl_title);
        TextView status = view.findViewById(R.id.book_content_status);

        title.setText(book.getTitle());
        status.setText(book.getStatus());

        return view;
    }
}
