package com.example.book_master.models;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;
    private String username;
    private String contactInfo;

    /**
     * Required by Firebase
     */
    public User() {
        username = "";
        password = "";
        contactInfo = "";
        email = "";
    }

    public User(String email, String password, String username, String contactInfo){
        this.email = email;
        this.password = password;
        // TODO: check if the username is unique
        this.username = username;
        this.contactInfo = contactInfo;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getContactInfo() { return contactInfo; }

    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}
