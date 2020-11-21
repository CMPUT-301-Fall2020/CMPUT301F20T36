package com.example.book_master.models;

import android.content.Context;
import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Store one specific user info
 * Could invoke methods as Owner & Borrower
 */
public class User implements Serializable, Owner, Borrower {
    private String email;
    private String password;
    private String username;
    private String contactInfo;

    /**
     * Empty constructor required by Firebase
     */
    public User() {
        email = "";
        password = "";
        username = "";
        contactInfo = "";
    }

    /**
     * Constructor
     * @param email user email, unique
     * @param password user password
     * @param username username, its uniqueness is checked in RegisterFrag
     * @param contactInfo user contact information
     */
    public User(String email, String password, String username, String contactInfo){
        this.email = email;         // could not be modified
        this.password = password;   // could not be modified
        this.username = username;
        this.contactInfo = contactInfo;
    }

    /**
     * @return user email
     */
    public String getEmail() { return email; }

    /**
     * @return user password
     */
    public String getPassword() { return password; }

    /**
     * @return username
     */
    public String getUsername() { return username; }

    /**
     * @param username the modified username
     * @return true if the username is successfully set (i.e., it is unique), false otherwise
     */
    public boolean setUsername(String username) {
        if (UserList.checkUnique(username)) {
            this.username = username;
            return true;
        }
        return false;
    }

    /**
     * @return user contact information
     */
    public String getContactInfo() { return contactInfo; }

    /**
     * User can edit contect info in my profile
     * @param contactInfo: user contact information
     */
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    // ------Borrower implementation------

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
    // ------End of Borrower------

    // ------Owner implementation------

    /**
     * Add one owned book to Firebase
     * @param book Book instance to be added
     * @param context Context of the window where Toast should be displayed
     * @return true if the book is successfully added, false otherwise
     */
    public Boolean Add_Book_Owned(Book book, Context context) {
        // the book has been recorded in userList
        if (BookList.getBook(book.getISBN()) != null) {
            return false;
        }

        book.setOwner(username);
        DBHelper.setBookDoc(book.getISBN(), book, context);
        return true;
    }
  
    /**
     * Owner view a list of all my books filtered by status
     */

    public ArrayList<Book> Get_Owned_Books(String status){ // Must and only select one status
        ArrayList<Book> ownedBookList = BookList.getOwnedBook(username);
        ArrayList<Book> temp = new ArrayList<Book>();
        for (Book book : ownedBookList) {
            if (book.getStatus().equalsIgnoreCase(status)) {
                temp.add(book);
            }
        }
        return temp;
    }

    /**
     * Owner remove one owned book from Firebase
     * @param ISBN ISBN of the Book instance to be deleted
     * @param context Context of the window where Toast should be displayed
     * @return true if the book is successfully deleted, false otherwise
     */
    public void Remove_Owned_Book(String ISBN, Context context) {
        DBHelper.deleteBookDoc(ISBN, context);
    }

    /**
     * Owner can set/edit the book description in my books
     */
    public Boolean Set_Book_description(String title, String author, Book book){
        if (BookList.getBook(book.getISBN()) == null)
            return false;

        book.setAuthor(author);
        book.setTitle(title);
        return true;
    }
    /**
     * Owner can view the book description in my books
     */
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


    public Boolean Accept_Requesting(Borrower borrower, Book book, Location location){
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

    // ------End of Owner------
}
