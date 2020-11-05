package com.example.book_master.models;

import java.util.ArrayList;

public class BookList {
    private static ArrayList<Book> bookList = new ArrayList<>();;

    public static void addBook(Book book) {bookList.add(book);}
    // deleting should be performed through DBHelper
    // call DBHelper.deleteUserDoc(username, context) in one activity
    // and the change shall be automatically notified and saved in UserList

    // clear the list, required by DBHelper
    public static void clearList(){
        bookList.clear();
    }

    public static Book getBook(String ISBN) {
        for (Book iter : bookList) {
            if (iter.getISBN() != null && iter.getISBN().equalsIgnoreCase(ISBN)) {
                return iter;
            }
        }
        return null;
    }

    public static ArrayList<Book> searchDesc(String keyword) {
        ArrayList<Book> qualifiedBooks = new ArrayList<>();
        for (Book iter : bookList) {
            if (iter.getDescription().contains(keyword)) {
                qualifiedBooks.add(iter);
            }
        }
        return qualifiedBooks;
    }
}
