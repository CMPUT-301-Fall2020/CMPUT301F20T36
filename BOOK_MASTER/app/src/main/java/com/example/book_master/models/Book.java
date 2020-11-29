package com.example.book_master.models;

import java.io.Serializable;

/**
 * Store one specific book info.
 * The book info includes Status as one of  available, requested, accepted, or borrowed.
 */
public class Book implements Serializable {
    /**
     * US 01.03.01
     * As an owner or borrower, I want a book to have a status to be one of: available, requested, accepted, or borrowed.
     */
    public final static String AVAILABLE = "AVAILABLE";
    public final static String REQUESTED = "REQUESTED";
    public final static String ACCEPTED = "ACCEPTED";
    public final static String BORROWED = "BORROWED";
    public final static String CONFIRM_BORROWED = "CONFIRM_BORROWED";
    public final static String RETURN = "RETURN";
    public final static String CONFIRM_RETURN = "CONFIRM_RETURN";

    private String title;
    private String author;
    private String ISBN;
    // use username to identify the user instance
    // and then call UserList.getUser(username) to get User instance
    private String owner;
    private String borrower;
    private String status;

    /**
     * Empty constructor required by Firebase
     */
    public Book() {
        title = "";
        author = "";
        ISBN = "";
        status = AVAILABLE;
        owner = "";
        borrower = "";
    }

    /**
     * Constructor
     * @param title book title
     * @param author book author
     * @param ISBN book ISBN, unique
     */
    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.status = AVAILABLE;
        this.owner = "";
        this.borrower = "";
    }

    /**
     * Constructor
     * @param title book title
     * @param author book author
     * @param ISBN book ISBN, unique
     * @param owner current owner
     * @param borrower current borrower
     */
    public Book(String title, String author, String ISBN, String owner, String borrower) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.owner = owner;
        this.borrower = borrower;
        status = AVAILABLE;
    }

    /**
     * @return book title
     */
    public String getTitle() { return title; }

    /**
     * @param title book title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * @return book author
     */
    public String getAuthor() { return author; }

    /**
     * @param author book author
     */
    public void setAuthor(String author) { this.author = author; }

    /**
     * @return book ISBN
     */
    public String getISBN() { return ISBN; }

    /**
     * @param ISBN book ISBN
     */
    public void setISBN(String ISBN) { this.ISBN = ISBN; }

    /**
     * @return current owner
     */
    public String getOwner() { return owner; }

    /**
     * @param owner current owner
     */
    public void setOwner(String owner) { this.owner = owner; }

    /**
     * @return current borrower
     */
    public String getBorrower() { return borrower; }

    /**
     * @param borrower current borrower
     */
    public void setBorrower(String borrower) { this.borrower = borrower; }

    /**
     * @return current status
     */
    public String getStatus() { return status; }

    /**
     *
     * @param status current status
     */
    public void setStatus(String status) { this.status = status; }
}
