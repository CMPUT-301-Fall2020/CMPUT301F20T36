
package com.example.book_master.models;

import java.util.ArrayList;
import java.io.Serializable;

public class UserList  implements Serializable {
    private static ArrayList<User> userList= new ArrayList<>();

    public static User getUser(String username){
        // when we search, do we care about casing
        User user = new User(username);
        if(userList.contains(user)){
            int index = userList.indexOf(user);
            return userList.get(index);
        }else{
            return null;
        }
    }

    public static User addUser(String username, String password, String contact){
        // need to guarantee the username is unique
        User new_user = new User(username, password, contact);
        if(!userList.contains(new_user)) {  // This will check properly if it is repeat
            userList.add(new_user);
            return new_user;  // what this return for? can we get away from boolean
        }else{
            return null;
        }
    }

    public static User deleteUser(String username, String password){
        User u = getUser(username);
//        if(Login(username, password)){  // Don't forgot to check if he is able to delete
//            userList.remove(u);
//        }
        // delete from data base
        return u;
    }

    /**
     * clear the list, required by DBHelper
     */
    public static void clearList(){
        userList.clear();
    }
}
