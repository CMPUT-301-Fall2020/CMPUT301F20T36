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

<<<<<<< HEAD
    public boolean Set_contactInfo(String userName, String contactInfo) {
        // do we need to check the login again?
        if(userName != null && contactInfo != null) {
            this.userName = userName;
            this.contactInfo = contactInfo;
            return true;
        } else return false;
    }
=======
    public String getContactInfo() { return contactInfo; }
>>>>>>> 978652025541111a7bec6ac119f2cd69d4bb0364

    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}
