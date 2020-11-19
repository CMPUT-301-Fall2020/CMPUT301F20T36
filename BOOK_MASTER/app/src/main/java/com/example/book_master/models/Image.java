package com.example.book_master.models;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;

public class Image {
    public String title;
    public StorageReference imageRef;

    public Image(String title, StorageReference imageRef) {
        this.title = title;
        this.imageRef = imageRef;
    }

    public String getTitle() { return title; }

    public StorageReference getImageRef() { return imageRef; }
}
