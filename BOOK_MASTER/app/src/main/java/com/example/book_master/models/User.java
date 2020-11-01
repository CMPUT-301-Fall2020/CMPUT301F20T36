package com.example.book_master.models;

import java.io.Serializable;

public class  User implements Comparable<User>, Serializable {
    private String userName;
    private String contactInfo;
    private String password;

    public User() {
        userName = "";
        contactInfo = "";
    }

    public User(String userName, String contactInfo, String password){
        // how to guarantee this username is unique when create
        this.userName = userName;
        this.contactInfo = contactInfo;
        this.password = password;
    }

    public User(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getContactInfo(){
        return contactInfo;
    }

//    public boolean Login(String password){
//        return (password.equals(this.password));
//    }

//    public boolean Reset_Password(String new_password, String old_password){
////        if(old_password.equals(password)){
////            if(new_password.equals(old_password)) {
////                return false;   // same password as old one
////            }else{
////                password = new_password;
////                return true;
////            }
////        }else{
////            return false;   // wrong old password
////        }
////    }

    public boolean Set_contactInfo(){
        // do we need to chack the login again?
        return false;
    }

    @Override
    public int compareTo(User u){
        return this.userName.toLowerCase().compareTo(u.getUserName().toLowerCase());
    }
}
