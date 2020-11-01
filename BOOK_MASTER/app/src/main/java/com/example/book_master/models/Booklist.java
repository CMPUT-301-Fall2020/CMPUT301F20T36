package com.example.book_master.models;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.io.Serializable;

public class Booklist implements Serializable {
    private static ArrayList<Book> booklist = new ArrayList<Book>();;

//    public static boolean addBook(String title, String author, String ISBN, User Owner) {
//        Book temp = new Book(title, author, ISBN, Owner);
//        booklist.add(temp);
//        return true;
//    }

    public static boolean addBook(Book book, User Owner) {
        booklist.add(book);
        book.setOwner(Owner);
//      add to database
        return true;
    }

    @NotNull
    public static Boolean deleteBook(String ISBN) {
        // search booklist
        Book temp = getBookDetails(ISBN);
        if (temp == null)
            return false;

        booklist.remove(temp);
//      delete to database
        return true;
    }

    public static Boolean deleteBook(Book book) {
        if (booklist.contains(book) == false)
            return false;

        booklist.remove(book);
//      delete to database
        return true;
    }

    @Nullable
    public static Book getBookDetails(String ISBN) {
        for (Book book : booklist) {
            if (book.getISBN() == ISBN) {
                return book;
            }
        }

        return null;
    }

    public static ArrayList<Book> searchBook(String keyword) {
        ArrayList<Book> temp_list = new ArrayList<Book>();
        for (Book book : booklist) {
            if (book.getISBN().equalsIgnoreCase(keyword) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                temp_list.add(book);
            }
        }
        return temp_list;
    }

    public static boolean addPhoto(String filepath) {
        return false;
    }

    public static boolean deletePhoto(String filepath) {
        return false;
    }

    public static boolean changeStatus(Book.Status status, String ISBN) {
        Book temp = getBookDetails(ISBN);
        if (temp == null)
            return false;

        temp.setStatus(status);
        return true;
    }

    public static boolean changeHolder(User holder, String ISBN) {
        Book temp = getBookDetails(ISBN);
        if (temp == null | holder == null)
            return false;

        temp.setHolder(holder);
        return true;
    }
}
