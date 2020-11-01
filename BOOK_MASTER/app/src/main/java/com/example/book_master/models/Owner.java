
package com.example.book_master.models;

import android.location.Location;
import java.io.Serializable;

import com.example.book_master.models.*;

import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

public class Owner extends User implements Serializable {
    private String username;
    private ArrayList<Message> messagelist;
    private ArrayList<Book> ownedBooks;

    public Owner(String name){
        super(name);
    }

    public Owner(String userName, String contactInfo, String password){
        super(userName, contactInfo, password);
    }

    public Boolean Add_Book_Owned(Book book){
        if (ownedBooks.contains(book) || book == null){
            return false;
        }
        else {
            ownedBooks.add(book);
            Booklist.addBook(book, this);
            return true;
        }
    }


    public ArrayList<Book> Get_Owned_Books(Book.Status status){ // Must and only select one status
        ArrayList<Book> books = new ArrayList<Book>();
        for (int i=0; i<ownedBooks.size(); i++){
            if (ownedBooks.get(i).getStatus() == status.toString()){
                books.add(ownedBooks.get(i));
            }
        }
        return books;
    }


    public Boolean Remove_Owned_Books(Book book){
        if (ownedBooks.contains(book)){
            ownedBooks.remove(book);
            Booklist.deleteBook(book.getISBN());
            return true;
        }

        return false;
    }


    public Boolean Set_Book_description(String isbn, String title, String author, Book book){
        if (ownedBooks.contains(book) == false)
            return false;

        book.setISBN(isbn);
        book.setAuthor(author);
        book.setTitle(title);
        return true;
    }


    public Boolean Show_Requested_User(Book book){
        /* it is supposed to fetch the requests and the user from fire store */
        return false;
    }


    public Boolean Accepte_Requesting(Borrower borrwoer, Book book, Location location){
        /* it is supposed to fetch the requests and the user from fire store */
        return false;
    }

    public Boolean Decline_Requesting(Borrower borrower, Book book){
        /* it is supposed to fetch the requests and the user from fire store */
        return false;
    }

    public Boolean Hand_Over_Book(){
        return false;
    }

    public Boolean Confirm_Return(){
        return false;
    }

    public void Show_Message(){

    }

//    public Book Search_Book(String title) {  isn't this should be searched as ISBN?
////         This need to change to search in library!
//        for (int i =0; i< ownedBooks.size(); i++){
//            if(ownedBooks.get(i).getTitle() == title){
//                return ownedBooks.get(i);
//            }
//        }
//    }

    public Book Search_Book_By_ISBN() {
        String ISBN = "123-4";
        return Booklist.getBookDetails(ISBN);  // NUllable, error in activity
    }

//    Delete() {
//        for (Book book : ownedBooks)
//            Booklist.deleteBook(book);
//    }
}
