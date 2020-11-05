package com.example.book_master.models;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class  User implements Comparable<User> {
    private String userName;
    private String contactInfo;
    private String password;
    private ArrayList<Message> messagelist;
    private ArrayList<Book> ownedBooks;

    public User() {
        userName = "";
        contactInfo = "";
    }

    public User(String userName, String contactInfo){
        // how to guarantee this username is unique when create
        this.userName = userName;
        this.contactInfo = contactInfo;
    }

    public User(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getContactInfo(){
        return contactInfo;
    }

//    public boolean Login(String password){
//        return (password.equals(this.password));
//    }

//    public boolean Reset_Password(String new_password, String old_password){
////        if(old_password.equals(password)){
////            if(new_password.equals(old_password)) {
////                return false;   // same password as old one
////            }else{
////                password = new_password;
////                return true;
////            }
////        }else{
////            return false;   // wrong old password
////        }
////    }

    public boolean Set_contactInfo(){
        // do we need to chack the login again?
        return false;
    }


    // -----------------BEGIN of Borrower Code---------------------------------------------
    public ArrayList<Book> Search_Book(String keyword){
        return Booklist.searchBook(keyword);
    }

    public ArrayList<Book> Show_Requesting_Book(Book book) {
//        ArrayList<Book> books = new ArrayList<Book>();
//        if (Requesting(book) == true) {
//            return books;
//        }
        return null;
    }

    public ArrayList<Book> Show_Accepted_Book(Book book) {
//        ArrayList<Book> books = new ArrayList<Book>();
        return null;
    }

    public Boolean Confirm_Borrowed(Book book) {
        return false;
    }

    public Boolean Return_Borrowed_Book(Book book) {
        return false;
    }

    public void Show_Message() {
    }
    // -----------------END of Borrower Code---------------------------------------------



    // -----------------BEGIN of Owner Code---------------------------------------------
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

    public Book Search_Book_By_ISBN() {
        String ISBN = "123-4";
        return Booklist.getBookDetails(ISBN);  // NUllable, error in activity
    }
    // -----------------END of Owner Code---------------------------------------------


    @Override
    public int compareTo(User u){
        return this.userName.toLowerCase().compareTo(u.getUserName().toLowerCase());
    }
}
