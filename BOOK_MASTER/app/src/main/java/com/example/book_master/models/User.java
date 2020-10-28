package com.example.book_master.models;

public class  User implements Comparable<User>{
    private String userName;
    private String contactInfo;
    private String password;

    public User(String n, String c, String p){
        this.userName = n;
        this.contactInfo = c;
        this.password = p;
    }
    public User(String n){
        this.userName = n;
    }
    public String getUserName() {
        return userName;
    }
    public String getContactInfo(){
        return contactInfo;
    }
    public boolean Login(String password){
        return (password.equals(this.password));
    }
    public boolean Reset_Password(String new_p, String old_p){
        if(old_p.equals(password)){
            if(new_p.equals(old_p)){
                return false;   // same password as old one
            }else{
                password = new_p;
                return true;
            }
        }else{
            return false;   // wrong old password
        }
    }

    public boolean Set_contactInfo(){
        return false;
    }

    @Override
    public int compareTo(User u){
        return this.userName.compareTo(u.getUserName());
    }
}
