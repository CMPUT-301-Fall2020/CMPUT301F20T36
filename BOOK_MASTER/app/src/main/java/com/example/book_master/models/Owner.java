package com.example.book_master.models;

import android.location.Location;

import com.example.book_master.models.*;

import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

public class Owner extends User {
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
        if (ownedBooks.contains(book)){
            return false;
        }
        else {
            ownedBooks.add(book);
            // add to library
            Booklist.addBook(book, this);
            return true;
        }
    }


    public ArrayList<Book> Get_Owned_Books(Book.Status status){
        ArrayList<Book> books = new ArrayList<Book>();
        for (int i=0; i<ownedBooks.size(); i++){
            if (ownedBooks.get(i).getStatus() == status){
                books.add(ownedBooks.get(i));
            }
        }
//        Just demonstrating another way to implement
//        for (Book book : ownedBooks) {
//            if (book.getStatus() == status) {
//                books.add(book);
//            }
//        }

        return books;
    }


    public Boolean Remove_Owned_Books(Book book){
        if (ownedBooks.contains(book)){
            ownedBooks.remove(book);
            // also need to remove from the library
            Booklist.deleteBook(book.getISBN());
            return true;
        }

        return false;
    }


    public Boolean Set_Book_description(String isbn, String title, String author, Book book){
        int index = ownedBooks.indexOf(book);
        if (index != -1){
            ownedBooks.get(index).setISBN(isbn);
            ownedBooks.get(index).setAuthor(author);
            ownedBooks.get(index).setTitle(title);
            return true;
            //This can be done just by calling book.setISBN(isbn) ....
        }
        return false;
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
        //The following ISBN should be obtained by calling ISBN scan function
        String ISBN = "123-4";
        return Booklist.getBookDetails(ISBN);
    }
}
