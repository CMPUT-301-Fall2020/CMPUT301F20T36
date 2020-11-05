
package com.example.book_master.models;

import java.util.ArrayList;

public class UserList {
    private static ArrayList<User> userList= new ArrayList<>();
    private static User currentUser;

    public static void addUser(User user) {userList.add(user);}
    // deleting should be performed through DBHelper
    // call DBHelper.deleteUserDoc(username, context) in one activity
    // and the change shall be automatically notified and saved in UserList

    // clear the list, required by DBHelper
    public static void clearList(){
        userList.clear();
    }

    public static User getUser(String username) {
        for (User iter : userList) {
            if (iter.getUsername() != null && iter.getUsername().equalsIgnoreCase(username)) {
                return iter;
            }
        }
        return null;
    }

    public static User getCurrentUser() {return currentUser;}

    public static void setCurrentUser(String email) {
        for (User iter : userList) {
            if (iter.getEmail() != null && iter.getEmail().equalsIgnoreCase(email)) {
                currentUser = iter;
                return;
            }
        }
    }
}
