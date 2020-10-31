package com.example.book_master.models;

public class User {
    private String userName;
    private String contactInfo;

    public User(String userName, String contactInfo) {
        this.userName = userName;
        this.contactInfo = contactInfo;
    }

    public String getUserName() {
        return userName;
    }
    public String getContactInfo() {
        return contactInfo;
    }

    public boolean setContactInfo(String newContactInfo) {
        contactInfo = newContactInfo;
        return false;
    }
}