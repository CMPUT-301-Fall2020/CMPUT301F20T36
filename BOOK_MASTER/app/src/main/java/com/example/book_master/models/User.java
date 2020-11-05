package com.example.book_master.models;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable, Owner, Borrower{
    private String email;
    private String password;
    private String username;
    private String contactInfo;

    /**
     * Required by Firebase
     */
    public User() {
        username = "";
        password = "";
        contactInfo = "";
        email = "";
    }

    public User(String email, String password, String username, String contactInfo){
        this.email = email;
        this.password = password;
        // TODO: check if the username is unique
        this.username = username;
        this.contactInfo = contactInfo;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getContactInfo() {
        return contactInfo;
    }

    public boolean Set_contactInfo(String contactInfo) {
        // do we need to check the login again?
        if(contactInfo != null) {
            this.contactInfo = contactInfo;
            return true;
        } else
            return false;
    }

    // Below is borrower
    public ArrayList<Book> Search_Book(String keyword){
        return BookList.searchDesc(keyword);
    }

    public ArrayList<Book> Show_Requesting_Book(Book book) {
//        ArrayList<Book> books = new ArrayList<Book>();
//        if (Requesting(book) == true) {
//            return books;
//        }
        return null;
    }

    public ArrayList<Book> Show_Accepted_Book(Book book){
//        ArrayList<Book> books = new ArrayList<Book>();
        return null;
    }

    public Boolean Confirm_Borrowed(Book book){
//        if(borrow.Borrowed_Book()){
//            return true;
//        }
//        else {
//            return false;
//        }
        return false;
    }

    public Boolean Return_Borrowed_Book(Book book){
//        if(borrow.Recieve_Book()){
//            return true;
//        }
//        else{
//            return false;
//        }
        return false;
    }
    // end of borrower

    // begin of owner
    public Boolean Add_Book_Owned(Book book){
        if (BookList.getBook(book.getISBN()) != null) {
            return false;
        }

        BookList.addBook(book);
        book.setOwner(username);
        return true;
    }


    public ArrayList<Book> Get_Owned_Books(String status){ // Must and only select one status
        ArrayList<Book> ownbooklist = BookList.getOwnedBook(username);
        ArrayList<Book> temp = new ArrayList<Book>();
        for (Book book : ownbooklist) {
            if (book.getStatus().equalsIgnoreCase(status)) {
                temp.add(book);
            }
        }
        return temp;
    }


    public Boolean Remove_Owned_Book(Book book){
        if (book == null || book.getOwner() != username)
            return false;

        return BookList.delete(book);
    }


    public Boolean Set_Book_description(String title, String author, Book book){
        if (BookList.getBook(book.getISBN()) == null)
            return false;

        book.setAuthor(author);
        book.setTitle(title);
        return true;
    }

    public Boolean Get_Book_description(Book book){
        if (book == null || book.getOwner() != username)
            return false;

        String myISBN = book.getISBN();
        String myAuthor = book.getAuthor();
        String myBookTitle = book.getTitle();
        return true;
    }


    public ArrayList<User> Show_Requested_User(Book book) {
        /* it is supposed to fetch the requests and the user from fire store */
        if (book == null || book.getOwner() != username)
            return null;

        ArrayList<Message> messages = MessageList.searchISBN(book.getISBN());
        ArrayList<User> users = new ArrayList<User>();
        for (Message msg : messages) {
            users.add(UserList.getUser(msg.getSender()));
        }

        return users;
    }


    public Boolean Accepte_Requesting(Borrower borrower, Book book, Location location){
        /* it is supposed to fetch the requests and the user from fire store */
        if (book == null || book.getOwner() != username)
            return false;

        /* US 09.01.01 specify a geolocation */

        // ownedBooks.get(book.getISBN()).setStatus("accepted")
//        for (int i=0; i<ownedBooks.size(); i++){
//            if (ownedBooks.get(i).getISBN() == book.getISBN()){
//                if (ownedBooks.get(i).getStatus() == "accepted")
//                    Decline_Requesting(borrower, book);
//                return false;
//                else ownedBooks.get(i).setStatus("accepted");
//            }
//        }
        return true;
    }

    public Boolean Decline_Requesting(Borrower borrower, Book book){
        /* it is supposed to fetch the requests and the user from fire store */
        if (book == null || book.getOwner() != username)
            return false;

//        Message declineMessage = new Message();
//        declineMessage.Add_New_Message("Declined!!");
        return true;
    }

    public Boolean Hand_Over_Book(String ISBN) {
        if (BookList.getBook(ISBN) == null || BookList.getBook(ISBN).getOwner() != username)
            return false;

//        ScanISBN handoverBookISBN = new ScanISBN(ISBN);
//        for (int i=0; i<ownedBooks.size(); i++) {
//            if (ownedBooks.get(i).getISBN() == handoverBookISBN.getScan_ISBN())
//                ownedBooks.setStatus("borrowed");
//        }
        return true;
    }

    public Boolean Confirm_Return(String ISBN) {
        return false;
    }
}
