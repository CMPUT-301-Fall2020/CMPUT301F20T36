package com.example.book_master.models;

public class Book {
    enum Status {unbooked, requested, accepted, borrowed, confirming_B, confirming_R};
    private String title;
    private String author;
    private String ISBN;
    private User owner;
    private User holder;
//    private Photographlist photolist;
    private Status status = Status.unbooked;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.owner = null;
        this.holder = null;
    }

    public Book(String title, String author, String ISBN, User owner) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.owner = owner;
        this.holder = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

//    public Photographlist getPhotolist() {
//        return photolist;
//    }

//    public void addPhotolist(Photographlist photolist) {
//        this.photolist = photolist;
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
