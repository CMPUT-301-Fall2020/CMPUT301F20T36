
package com.example.book_master.models;

import android.location.Location;
import java.io.Serializable;

import com.example.book_master.models.*;

import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

public interface Owner {

//    public Owner(String name){
//        super(name);
//    }
//
//    public Owner(String userName, String contactInfo, String password){
//        super(userName, contactInfo, password);
//    }

    public Boolean Add_Book_Owned(Book book);


    public ArrayList<Book> Get_Owned_Books(Book.Status status);


    public Boolean Remove_Owned_Books(Book book);


    public Boolean Set_Book_description(String isbn, String title, String author, Book book);


    public Boolean Show_Requested_User(Book book);


    public Boolean Accepte_Requesting(Borrower borrwoer, Book book, Location location);

    public Boolean Decline_Requesting(Borrower borrower, Book book);

    public Boolean Hand_Over_Book();

    public Boolean Confirm_Return();

    public void Show_Message();

    public Book Search_Book_By_ISBN();

//    Delete() {
//        for (Book book : ownedBooks)
//            Booklist.deleteBook(book);
//    }
}
