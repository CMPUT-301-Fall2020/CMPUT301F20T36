package com.example.book_master.models;

public class ScanISBN {
    public int Scan_ISBN;

    /* Constructor */
    public ScanISBN(int scan_ISBN) {
        Scan_ISBN = scan_ISBN;
    }

    /**
     * @param Scan_ISBN: the unique identification code for the book
     */

    /* getter and setter */
    public int getScan_ISBN() {
        return Scan_ISBN;
    }

    public void setScan_ISBN(int scan_ISBN) {
        Scan_ISBN = scan_ISBN;
    }
}

