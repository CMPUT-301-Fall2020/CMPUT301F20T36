package com.example.book_master;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for BookList
 * Notice functionality related to Firebase could not be tested (it could not connect to cloud)
 */
public class BookListTest {
    private Book mockBook() {
        final String title = "ECE 410: Digital Logic System Design";
        final String author = "Bruce Cockburn";
        final String ISBN = "978-3-16-148410-0";
        final String owner = "Shrike";
        final String borrower = "Shrike's friend";
        return new Book(title, author, ISBN, owner, borrower);
    }

    /**
     * Test: addBook(Book book), getBook(String ISBN)
     */
    @Test
    void testAddGet() {
        BookList.clearList();

        Book temp = mockBook();
        String ISBN = temp.getISBN();

        BookList.addBook(temp);
        assertTrue(BookList.getBook(ISBN).getISBN().equalsIgnoreCase(ISBN));
    }

    /**
     * Test: clearList()
     */
    @Test
    void testClear() {
        BookList.clearList();

        Book temp = mockBook();
        String ISBN = temp.getISBN();

        BookList.addBook(temp);
        BookList.clearList();
        Book retrieved = BookList.getBook(ISBN);
        assertNull(retrieved);
    }

    /**
     * Test: getOwnedBook(String username), getBorrowedBook(String username)
     */
    @Test
    void testGetOwnedBorrowed() {
        BookList.clearList();

        Book temp = mockBook();
        String ISBN = temp.getISBN();

        BookList.addBook(temp);
        ArrayList<Book> owned = new ArrayList<>();
        owned = BookList.getOwnedBook("Shrike");
        assertTrue(owned.size() != 0 && owned.get(0).getISBN().equalsIgnoreCase(ISBN));
        owned = BookList.getOwnedBook("");
        assertTrue(owned.size() == 0);

        ArrayList<Book> borrowed = new ArrayList<>();
        borrowed = BookList.getBorrowedBook("Shrike's friend");
        assertTrue(borrowed.size() != 0 && borrowed.get(0).getISBN().equalsIgnoreCase(ISBN));
        borrowed = BookList.getBorrowedBook("");
        assertTrue(borrowed.size() == 0);
    }

    /**
     * Test: getAvailableBook(String username)
     */
    @Test
    void testGetAvailableBook() {
        BookList.clearList();

        Book temp = mockBook();
        String ISBN = temp.getISBN();

        BookList.addBook(temp);
        ArrayList<Book> available = new ArrayList<>();
        available = BookList.getAvailableBook("not Shrike");
        assertTrue(available.size() != 0 && available.get(0).getISBN().equalsIgnoreCase(ISBN));
        temp.setStatus("BORROWED");
        available = BookList.getAvailableBook("not Shrike");
        assertTrue(available.size() == 0);
    }

    /**
     * Test: searchDesc(String keyword, String username)
     */
    @Test
    void testSearchDesc() {
        BookList.clearList();

        Book temp = mockBook();
        String ISBN = temp.getISBN();

        BookList.addBook(temp);
        ArrayList<Book> qualified = new ArrayList<>();
        qualified = BookList.searchDesc("410", "not Shrike");
        assertTrue(qualified.size() != 0 && qualified.get(0).getISBN().equalsIgnoreCase(ISBN));
        qualified = BookList.searchDesc("410", "Shrike");
        assertTrue(qualified.size() == 0);
        // any book that is available
        qualified = BookList.searchDesc("", "not Shrike");
        assertTrue(qualified.size() != 0 && qualified.get(0).getISBN().equalsIgnoreCase(ISBN));
        qualified = BookList.searchDesc("garbage input", "not Shrike");
        assertTrue(qualified.size() == 0);
    }
}
