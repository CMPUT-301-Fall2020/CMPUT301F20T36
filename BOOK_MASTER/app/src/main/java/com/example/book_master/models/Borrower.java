package com.example.book_master.models;

import java.util.ArrayList;

public class Borrower extends User{
    public Borrower(String name){
        super(name);
    }

    public Borrower(String name, String contact_info, String password){
        super(name, contact_info, password);
    }

    public Boolean Search_Book(String keyword){
        if(Search_Book(keyword)){
            return true;
        }
        else{
            return false;
        }
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
}
