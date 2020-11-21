
package com.example.book_master.models;

import android.content.Context;
import android.location.Location;
import java.io.Serializable;

import com.example.book_master.models.*;

import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

/**
 * Owner methods
 */
public interface Owner {
    /**
     * Add one owned book to Firebase
     * @param book Book instance to be added
     * @param context Context of the window where Toast should be displayed
     * @return true if the book is successfully added, false otherwise
     */
    public Boolean Add_Book_Owned(Book book, Context context);

    public ArrayList<Book> Get_Owned_Books(String status);

    /**
     * Remove one owned book from Firebase
     * @param ISBN ISBN of the Book instance to be deleted
     * @param context Context of the window where Toast should be displayed
     */
    public void Remove_Owned_Book(String ISBN, Context context);

    public Boolean Set_Book_description(String title, String author, Book book);

    public Boolean Get_Book_description(Book book);

    public ArrayList<User> Show_Requested_User(Book book);

    public Boolean Accept_Requesting(Borrower borrower, Book book, Location location);

    public Boolean Decline_Requesting(Borrower borrower, Book book);

    public Boolean Hand_Over_Book(String ISBN);

    public Boolean Confirm_Return(String ISBN);


}
