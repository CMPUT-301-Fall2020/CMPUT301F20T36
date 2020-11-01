package com.example.book_master.models;

import java.util.ArrayList;
import java.io.Serializable;

public interface Borrower{
//    private ArrayList<Message> messageList;

//    public Borrower(String name){
//        super(name);
//    }
//
//    public Borrower(String name, String contact_info, String password){
//        super(name, contact_info, password);
//    }

    public ArrayList<Book> Search_Book(String keyword);

    public ArrayList<Book> Show_Requesting_Book(Book book);

    public ArrayList<Book> Show_Accepted_Book(Book book);

    public Boolean Confirm_Borrowed(Book book);

    public Boolean Return_Borrowed_Book(Book book);

    public void Show_Message();
}
