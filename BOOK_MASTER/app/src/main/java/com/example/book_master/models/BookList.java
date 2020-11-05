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

    public static boolean delete(Book book) {
        if (bookList.contains(book) == false) {
            return false;
        }
        bookList.remove(book);
        return true;
    }

    public static ArrayList<Book> getOwnedBook (String username) {
        ArrayList<Book> temp = new ArrayList<Book>();
        for (Book book : bookList) {
            if (book.getOwner().equalsIgnoreCase(username)) {
                temp.add(book);
            }
        }
        return temp;
    }

    /**
     *
     * @param username
     * @return
     */
    public static ArrayList<Book> getBorrowedBook (String username) {
        ArrayList<Book> temp = new ArrayList<Book>();
        for (Book book : bookList) {
            if (book.getBorrower().equalsIgnoreCase(username)) {
                temp.add(book);
            }
        }
        return temp;
    }

    public static Book getBook(String ISBN) {
        for (Book iter : bookList) {
            if (iter.getISBN() != null && iter.getISBN().equalsIgnoreCase(ISBN)) {
                return iter;
            }
        }
        return null;
    }

    public static ArrayList<Book> getAvailableBook(){
        ArrayList<Book> temp = new ArrayList<Book>();
        for(Book book : bookList){
            if(book.getStatus().equals(Book.AVALIABLE)){
                temp.add(book);
            }
        }
        return temp;
    }

    public static ArrayList<Book> searchDesc(String keyword) {
        ArrayList<Book> qualifiedBooks = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getISBN().contains(keyword)) {

                qualifiedBooks.add(book);
            }
        }
        return qualifiedBooks;
    }
}
