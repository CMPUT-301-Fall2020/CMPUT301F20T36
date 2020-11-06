package com.example.book_master.models;

import java.util.ArrayList;

/**
 * Static class receiving the asynchronous data updating from Firebase
 * Class and Activity should retrieve desired User info from here rather than invoking DBHelper
 */
public class BookList {
    private static ArrayList<Book> bookList = new ArrayList<>();;

    /**
     * adding & deleting should be performed through DBHelper
     * call
     *  DBHelper.setBookDoc(ISBN, book, context)
     *  DBHelper.deleteBookDoc(ISBN, context)
     * in one activity, and the change shall be automatically notified and saved in BookList
     */

    /**
     * Add one Book instance to bookList
     * @param book: Book instance to be added
     */
    public static void addBook(Book book) {
        if (!bookList.contains(book)) {
            bookList.add(book);
        }
    }

    /**
     * Delete one Book instance in bookList
     * @param book: Book instance to be deleted
     */
    public static void deleteBook(Book book) {
        if (bookList.contains(book)) {
            bookList.remove(book);
        }
    }

    /**
     * clear the list, required by DBHelper
     */
    public static void clearList() { bookList.clear(); }

    /**
     * Get all Books that one User owned
     * @param username: username of the desired User
     * @return ArrayList<Book>
     */
    public static ArrayList<Book> getOwnedBook (String username) {
        ArrayList<Book> temp = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getOwner().equalsIgnoreCase(username)) {
                temp.add(book);
            }
        }
        return temp;
    }

    /**
     * Get all Books that one User borrowed
     * @param username: username of the desired User
     * @return ArrayList<Book>
     */
    public static ArrayList<Book> getBorrowedBook (String username) {
        ArrayList<Book> temp = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBorrower().equalsIgnoreCase(username)) {
                temp.add(book);
            }
        }
        return temp;
    }

    /**
     * Search through bookList to get the Book instance which has the ISBN specified
     * @param ISBN: ISBN, unique
     * @return Book
     */
    public static Book getBook(String ISBN) {
        for (Book book : bookList) {
            if (book.getISBN() != null && book.getISBN().equalsIgnoreCase(ISBN)) {
                return book;
            }
        }
        return null;
    }


    public static ArrayList<Book> getAvailableBook(String userName){
        ArrayList<Book> temp = new ArrayList<Book>();
        for(Book book : bookList){
            if((book.getStatus().equals(Book.AVAILABLE) || book.getStatus().equalsIgnoreCase(Book.REQUESTED)) && !book.getOwner().equals(userName)){
                temp.add(book);
            }
        }
        return temp;
    }


    /**
     * Get all Books that which has the description specified
     * @param keyword: keyword in description
     * @return ArrayList<Book>
     */
    public static ArrayList<Book> searchDesc(String keyword, String username) {
        ArrayList<Book> temp = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getISBN().contains(keyword)) {
                if((book.getStatus().equals(Book.AVAILABLE) || book.getStatus().equalsIgnoreCase(Book.REQUESTED)) && !book.getOwner().equals(username)) {
                    temp.add(book);
                }
            }
        }
        return temp;
    }
}
