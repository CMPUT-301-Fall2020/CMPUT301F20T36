package com.example.book_master;

import com.example.book_master.models.Book;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Book
 * Notice functionality related to Firebase could not be tested (it could not connect to cloud)
 */
public class BookTest {
    private Book mockBook() {
        final String title = "ECE 410: Digital Logic System Design";
        final String author = "Bruce Cockburn";
        final String ISBN = "978-3-16-148410-0";
        final String owner = "Shrike";
        final String borrower = "Shrike's friend";
        return new Book(title, author, ISBN, owner, borrower);
    }

    /**
     * getTitle(), setTitle(String title), getAuthor(), setAuthor(String author),
     * getISBN(), setISBN(String ISBN), getStatus(), setStatus(String status)
     * getOwner(), setOwner(String owner), getBorrower(), setBorrower(String borrower)
     */
    @Test
    void testSetGet() {
        Book temp = mockBook();
        assertTrue(temp.getTitle().equalsIgnoreCase("ECE 410: Digital Logic System Design"));
        assertTrue(temp.getAuthor().equalsIgnoreCase("Bruce Cockburn"));
        assertTrue(temp.getISBN().equalsIgnoreCase("978-3-16-148410-0"));
        assertTrue(temp.getOwner().equalsIgnoreCase("Shrike"));
        assertTrue(temp.getBorrower().equalsIgnoreCase("Shrike's friend"));
        assertTrue(temp.getStatus().equalsIgnoreCase("AVAILABLE"));

        temp.setTitle("w(ﾟДﾟ)w");
        temp.setAuthor("Σ( ° △ °|||)︴");
        temp.setISBN("978-3-16-148410-2");
        temp.setOwner("╰(*°▽°*)╯");
        temp.setBorrower("(￣_,￣ )");
        temp.setStatus("REQUESTED");
        assertTrue(temp.getTitle().equalsIgnoreCase("w(ﾟДﾟ)w"));
        assertTrue(temp.getAuthor().equalsIgnoreCase("Σ( ° △ °|||)︴"));
        assertTrue(temp.getISBN().equalsIgnoreCase("978-3-16-148410-2"));
        assertTrue(temp.getOwner().equalsIgnoreCase("╰(*°▽°*)╯"));
        assertTrue(temp.getBorrower().equalsIgnoreCase("(￣_,￣ )"));
        assertTrue(temp.getStatus().equalsIgnoreCase("REQUESTED"));
    }
}
