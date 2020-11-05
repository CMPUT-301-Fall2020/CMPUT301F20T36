
package com.example.book_master.models;

import java.util.ArrayList;

/**
 * Static class receiving the asynchronous data updating from Firebase
 * Class and Activity should retrieve desired User info from here rather than invoking DBHelper
 */
public class UserList {
    private static ArrayList<User> userList= new ArrayList<>();
    private static User currentUser;

    /**
     * adding & deleting should be performed through DBHelper
     * call
     *  DBHelper.setUserDoc(username, user, context)
     *  DBHelper.deleteUserDoc(username, context)
     * in one activity, and the change shall be automatically notified and saved in UserList
     */

    /**
     * Add one User instance to userList
     * @param user: User instance to be added
     */
    public static void addUser(User user) {
        if (!userList.contains(user)) {
            userList.add(user);
        }
    }

    /**
     * Delete one User instance in userList
     * @param user: User instance to be deleted
     */
    public static void deleteUser(User user) {
        if (userList.contains(user)) {
            userList.remove(user);
        }
    }

    /**
     * clear the list, required by DBHelper
     */
    public static void clearList() { userList.clear(); }

    /**
     * Set currentUser to the User instance corresponding to the current logged account
     * @param email: the current logged account email
     */
    public static void setCurrentUser(String email) {
        for (User user : userList) {
            if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)) {
                currentUser = user;
                return;
            }
        }
    }

    /**
     * Get the User instance corresponding to the current logged account
     * @return User
     */
    public static User getCurrentUser() {return currentUser;}

    /**
     * Search through userList to get the User instance which has the username specified
     * @param username: username, unique
     * @return User
     */
    public static User getUser(String username) {
        for (User user : userList) {
            if (user.getUsername() != null && user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Check if the username is duplicated
     * @param username: username to be checked
     * @return true if the username is unique, false otherwise
     */
    public static boolean checkUnique(String username) {
        for (User user : userList) {
            if (user.getUsername() != null && user.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }
}
