package com.example.book_master.models;

import java.util.ArrayList;
import java.io.Serializable;

public interface Borrower{
    public ArrayList<Book> Show_Requesting_Book(Book book);

    public ArrayList<Book> Show_Accepted_Book(Book book);

    public Boolean Confirm_Borrowed(Book book);

    public Boolean Return_Borrowed_Book(Book book);
}
