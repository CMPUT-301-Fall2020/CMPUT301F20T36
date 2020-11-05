package com.example.book_master.models;

import java.util.ArrayList;
import java.io.Serializable;

<<<<<<< Updated upstream
public interface Borrower{
//    private ArrayList<Message> messageList;

//    public Borrower(String name){
//        super(name);
//    }
//
//    public Borrower(String name, String contact_info, String password){
//        super(name, contact_info, password);
//    }

    public ArrayList<Book> Search_Book(String keyword);

    public ArrayList<Book> Show_Requesting_Book(Book book);

    public ArrayList<Book> Show_Accepted_Book(Book book);

    public Boolean Confirm_Borrowed(Book book);

    public Boolean Return_Borrowed_Book(Book book);

    public void Show_Message();
=======
public class Borrower extends User implements Serializable {
    private ArrayList<Message> messageList;

    public Borrower(String name){
        super(name);
    }

    public Borrower(String name, String contact_info){
        super(name, contact_info);
    }

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

    public void Show_Message(){

    }
>>>>>>> Stashed changes
}
