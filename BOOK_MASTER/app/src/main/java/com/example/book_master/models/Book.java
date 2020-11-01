package com.example.book_master.models;

public class Book {
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
    private int ISBN;
    private User owner;
    private User holder;
    private Photographlist photolist;
    private Status status = Status.avaliable;

    public Book(String title, String author, int ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public Book(String title, String author, int ISBN, User owner) {
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

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
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

    public Photographlist getPhotolist() {
        return photolist;
    }

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
>>>>>>> Stashed changes
