package com.example.book_master;

import com.example.book_master.models.User;
import com.example.book_master.models.UserList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserList
 * Notice functionality related to Firebase could not be tested (it could not connect to cloud)
 */
public class UserListTest {
    private User mockUser() {
        final String email = "lyon233333@gmail.com";
        final String password = "ntd10086";
        final String username = "Shrike";
        final String contactInfo = "QAQ";
        return new User(email, password, username, contactInfo);
    }

    /**
     * UserList.addUser(User user) & UserList.getUser(String username)
     */
    @Test
    void testAddGet() {
        User temp = mockUser();
        String username = temp.getUsername();

        UserList.addUser(temp);
        User retrieved = UserList.getUser(username);
        assertTrue(retrieved.getEmail().equalsIgnoreCase(mockUser().getEmail()) &&
                retrieved.getPassword().equalsIgnoreCase(mockUser().getPassword()) &&
                retrieved.getUsername().equalsIgnoreCase(mockUser().getUsername()) &&
                retrieved.getContactInfo().equalsIgnoreCase(mockUser().getContactInfo()));
    }

    /**
     * UserList.clearList()
     */
    @Test
    void testClear() {
        User temp = mockUser();
        String username = temp.getUsername();

        UserList.addUser(temp);
        UserList.clearList();
        User retrieved = UserList.getUser(username);
        assertNull(retrieved);
    }

//    /**
//     * UserList.setCurrentUser(String email) & UserList.getCurrentUser()
//     */
//    @Test
//    void testSetGetCurrentUser() {
//        User temp = mockUser();
//        String username = temp.getUsername();
//
//        UserList.addUser(temp);
//        UserList.setCurrentUser(username);
//        User retrieved = UserList.getCurrentUser();
//    }
}
