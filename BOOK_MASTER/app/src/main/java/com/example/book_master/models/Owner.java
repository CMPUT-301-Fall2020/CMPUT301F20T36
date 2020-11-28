package com.example.book_master.models;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;

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
    Boolean Add_Book_Owned(Book book, Context context);

    /**
     * Retrieve owned books with the status specified from firebase
     * @param status status specified
     * @return ArrayList<Book>
     */
    ArrayList<Book> Get_Owned_Books(String status);

    /**
     * Remove one owned book from Firebase
     * @param ISBN ISBN of the Book instance to be deleted
     * @param context Context of the window where Toast should be displayed
     */
    void Remove_Owned_Book(String ISBN, Context context);

    Boolean Set_Book_description(String title, String author, Book book);

    Boolean Get_Book_description(Book book);

    ArrayList<User> Show_Requested_User(Book book);

    Boolean Accept_Requesting(Borrower borrower, Book book, Location location);

    Boolean Decline_Requesting(Borrower borrower, Book book);

    Boolean Hand_Over_Book(String ISBN);

    Boolean Confirm_Return(String ISBN);
}
