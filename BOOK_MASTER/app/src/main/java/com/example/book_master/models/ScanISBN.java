package com.example.book_master.models;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScanISBN {
    public static int Scan_ISBN;

    /* Constructor */
    public ScanISBN(int scan_ISBN) {
        Scan_ISBN = scan_ISBN;
    }

    /* getter and setter */
    public int getScan_ISBN() {
        return Scan_ISBN;
    }

    public void setScan_ISBN(int scan_ISBN) {
        Scan_ISBN = scan_ISBN;
    }
}

