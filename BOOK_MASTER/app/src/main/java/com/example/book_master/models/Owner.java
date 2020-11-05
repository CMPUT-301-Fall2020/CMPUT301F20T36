
package com.example.book_master.models;

import android.content.Context;
import android.location.Location;
import java.io.Serializable;

import com.example.book_master.models.*;

import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

public interface Owner {
    public Boolean Add_Book_Owned(Book book, Context context);

    public ArrayList<Book> Get_Owned_Books(String status);

    public void Remove_Owned_Book(String ISBN, Context context);

    public Boolean Set_Book_description(String title, String author, Book book);

    public Boolean Get_Book_description(Book book);

    public ArrayList<User> Show_Requested_User(Book book);

    public Boolean Accepte_Requesting(Borrower borrower, Book book, Location location);

    public Boolean Decline_Requesting(Borrower borrower, Book book);

    public Boolean Hand_Over_Book(String ISBN);

    public Boolean Confirm_Return(String ISBN);


}
