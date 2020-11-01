package com.example.book_master.models;

import java.io.Serializable;

public class Book implements Serializable {
    enum Status {
        available("available"), requested("requested"), accepted("accepted"), borrowed("borrowed"), confirming_B("confirming_B"), confirming_R("confirming_R");
        private String statusName;
        private Status(String s){
            this.statusName = s;
        }
        @Override
        public String toString(){
            return statusName;
        }

    };
    private String title;
    private String author;
    private String ISBN;
    private User owner;
    private User holder;
    private PhotographList photolist;
    private Status status = Status.available;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public Book(String title, String author, String ISBN, User owner) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.owner = owner;
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

    public PhotographList getPhotolist() {
        return photolist;
    }

//    public void addPhotolist(Photographlist photolist) {
//        this.photolist = photolist;
//    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
*/