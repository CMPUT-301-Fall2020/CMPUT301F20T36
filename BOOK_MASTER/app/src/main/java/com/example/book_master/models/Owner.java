/*
package com.example.book_master.models;


import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

public class Owner extends User {
    private String username;
    private ArrayList<message> messagelist;
    private ArrayList<Book> ownedBooks;

    public Owner(String name){
        super(name);
    }

    public Owner(String n, String c, String p){
        super(n, c, p);
    }
    public Boolean Add_Book_Owned(Book book){
        if (ownedBooks.contains(book)){
            return false;
        }
        else {
            ownedBooks.add(book);
            return true;
        }
    }


    public ArrayList<Book> Get_Owned_Books(Enum status){
        ArrayList<Book> books = new ArrayList<Book>();
        for (int i=0; i<ownedBooks.size(); i++){
            if (ownedBooks.get(i).getstatus() == status){
                books.add(ownedBooks.get(i));
            }
        }

        return books;
    }


    public Boolean Remove_Owned_Books(Book book){
        if (ownedBooks.contains(book)){
            ownedBooks.remove(book);
            return true;
        }

        return false;
    }


    public Boolean Set_Book_description(ISBN isbn, Title title, Author author, Book book){
        int index = ownedBooks.indexOf(book);
        if (index != -1){
            ownedBooks.get(index).ISBN = isbn;
            ownedBooks.get(index).author = author;
            ownedBooks.get(index).tile = title;
            return true;
        }
        return false;
    }


    public Boolean Show_Requested_User(Book book){
        /* it is supposed to fetch the requests and the user from fire store */
  /*      return false;
    }


    public Boolean Accepte_Requesting(Borrower borrwoer, Book book, Location location){
        // it is supposed to fetch the requests and the user from fire store
        return false;
    }

    public Boolean Decline_Requesting(Borrower borrower, Book borrower){
        //it is supposed to fetch the requests and the user from fire store
        return false;
    }

    public Boolean Hand_Over_Book(){

    }

    Boolean Confirm_Return(){

    }

    void Show_Message(){

    }

    public Book Search_Book(String name) {
        for (int i =0; i< ownedBooks.size(); i++){
            if(ownedBooks.get(i).gettite() == name){
                return ownedBooks.get(i);
            }
        }
        return null;
    }
}
*/