/*package com.example.book_master.models;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> userList= new ArrayList<>();
1

    private User get_User(String name){
        User u = new User(name);
        if(userList.contains(u)){
            int index = userList.indexOf(u);
            return userList.get(index);
        }else{
            return null;
        }
    }

    public User Add_User(String name, String password, String contact){
        User new_user = new User(name, password, contact);
        if(!userList.contains(new_user)) {
            userList.add(new_user);
            return new_user;
        }else{
            return null;
        }
    }

    public User Delete_User(String name, String password){
        User u = get_User(name);
        if(Login(name, password)){
            userList.remove(u);
        }
        return u;
    }

    public boolean Login(String name, String password){
        User u = get_User(name);
        if(u != null){
            return u.Login(password);
        }
        return false;
    }
}
*/