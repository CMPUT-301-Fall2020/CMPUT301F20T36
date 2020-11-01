package com.example.book_master.models;

public class User {
    private String username;
    private String contactInfo;

    public User() {
        // public non-argument constructor needed fot Firestore
    }

    public User(String username, String contactInfo) {
        this.username = username;
        this.contactInfo = contactInfo;
    }

    public String getUsername() {
        return username;
    }
    public String getContactInfo() {
        return contactInfo;
    }

    public boolean setContactInfo(String newContactInfo) {
        contactInfo = newContactInfo;
        return false;
    }
}