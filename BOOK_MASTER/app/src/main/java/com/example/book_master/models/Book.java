package com.example.book_master.models;

import java.io.Serializable;

public class Book implements Serializable {

    public final static String AVALIABLE = "AVALIABLE";
    public final static String REQUESTED = "REQUESTED";
    public final static String ACCEPTED = "ACCEPTED";
    public final static String BORROWED = "BORROWED";
    public final static String CONFIRM_BORROWED = "CONFIRM_BORROWED";
    public final static String CONFIRM_RETURN = "CONFIRM_RETURN";

    private String title;
    private String author;
    private String ISBN;
    // use username to identify the user instance
    // and then call UserList.getUser(username) to get User instance
    private String owner;
    private String borrower;
    // TODO: implement a structure holding photograph
//    private PhotographList photographList;
    // status: available, requested, accepted, borrowed, confirming_B, confirming_R
    private String status;

    public Book() {
        title = "";
        author = "";
        ISBN = "";
        status = AVALIABLE;
    }
  
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        status = AVALIABLE;
        this.owner = null;
        this.borrower = null;
    }

    public Book(String title, String author, String ISBN, String owner, String borrower) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.owner = owner;
        this.borrower = null;
        status = AVALIABLE;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getISBN() { return ISBN; }

    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public String getOwner() { return owner; }

    public void setOwner(String owner) { this.owner = owner; }

    public String getBorrower() { return borrower; }

    public void setBorrower(String borrower) { this.borrower = borrower; }

//    public PhotographList getPhotographList() { return photographList; }
//
//    public void setPhotographList(PhotographList photographList) { this.photographList = photographList; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
