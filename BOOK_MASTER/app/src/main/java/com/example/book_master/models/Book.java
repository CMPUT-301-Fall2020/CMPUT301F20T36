package com.example.book_master.models;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private String description;
    // use username to identify the user instance
    // and then call UserList.getUser(username) to get User instance
    private String owner;
    private String holder;
    // TODO: implement a structure holding photograph
//    private PhotographList photographList;
    // status: available, requested, accepted, borrowed, confirming_B, confirming_R
    private String status;

    public Book() {
        title = "";
        author = "";
        ISBN = "";
    }
  
//    public Book(String title, String author, String ISBN) {
//        this.title = title;
//        this.author = author;
//        this.ISBN = ISBN;
//        this.description = null;
//        this.owner = null;
//        this.holder = null;
//    }

    public Book(String title, String author, String ISBN, String desc, String owner, String holder) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = desc;
        this.owner = owner;
        this.holder = holder;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getISBN() { return ISBN; }

    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    public String getDescription() {return description;}

    public void setDescription(String desc) {this.description = desc;}

    public String getOwner() { return owner; }

    public void setOwner(String owner) { this.owner = owner; }

    public String getHolder() { return holder; }

    public void setHolder(String holder) { this.holder = holder; }

//    public PhotographList getPhotographList() { return photographList; }
//
//    public void setPhotographList(PhotographList photographList) { this.photographList = photographList; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
