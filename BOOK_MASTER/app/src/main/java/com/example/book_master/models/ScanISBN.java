package com.example.practice_ropo;

public class scanISBN {
    public int Scan_ISBN;


    /* Constructor */
    public scanISBN(int scan_ISBN) {
        Scan_ISBN = scan_ISBN;
    }

    /**
     * @param scan_ISBN: the unique identification code for the book
     */

    /* getter and setter */
    public int getScan_ISBN() {
        return Scan_ISBN;
    }

    public void setScan_ISBN(int scan_ISBN) {
        Scan_ISBN = scan_ISBN;
    }
}

