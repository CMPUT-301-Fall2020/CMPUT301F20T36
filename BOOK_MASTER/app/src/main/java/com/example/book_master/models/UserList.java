
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

    public static User addUser(String username, String password, String contact, String email) {
        // need to guarantee the username is unique
        User new_user = new User(username, password, contact, email);
        if (!userList.contains(new_user)) {  // This will check properly if it is repeat
            userList.add(new_user);
            return new_user;  // what this return for? can we get away from boolean
        } else {
            return null;
        }
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

    public static boolean checkUnique(String username) {
        for (User user : userList) {
            if (user.getUsername() == username)
                return false;
        }
        return true;
    }
}
