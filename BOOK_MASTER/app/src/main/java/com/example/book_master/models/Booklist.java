
package com.example.book_master.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Booklist {
    private ArrayList<Book> booklist;

    public Booklist() {
        booklist = new ArrayList<Book>();
    }

    public boolean addBook(String title, String author, String ISBN, User Owner) {
        Book temp = new Book(title, author, ISBN, Owner);
        booklist.add(temp);
        return true;
    }

    public Boolean deleteBook(int ISBN) {
        // search booklist
        return false;
    }

    public Book getBookDetails(int ISBN) {
        Book temp = new Book("Temp", "Temp", "1234");
        return temp;
    }

    public Book searchBook(String keyword) {
        Book temp = new Book("Temp", "Temp", "1234");
        return temp;
    }

    public boolean addPhoto(String filepath) {
        return false;
    }

    public boolean deletePhoto(String filepath) {
        return false;
    }

    public boolean changeStatus(Book.Status status, int ISBN) {
        return false;
    }

    public boolean changeHolder(User holder, int ISBN) {
        return false;
    }
}
*/