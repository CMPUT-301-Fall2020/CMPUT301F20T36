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
     * Test: addUser(User user), getUser(String username)
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
     * Test: clearList()
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

    /**
     * Test: setCurrentUser(String email), getCurrentUser()
     */
    @Test
    void testSetGetCurrentUser() {
        User temp = mockUser();
        String email = temp.getEmail();
        String username = temp.getUsername();

        UserList.addUser(temp);
        UserList.setCurrentUser(email);
        User retrieved = UserList.getCurrentUser();
        assertTrue(retrieved.getUsername().equalsIgnoreCase(username));
    }

    /**
     * Test: checkUnique(String username)
     */
    @Test
    void testCheckUnique() {
        User temp = mockUser();
        String username = temp.getUsername();

        assertTrue(UserList.checkUnique(username));
        UserList.addUser(temp);
        assertFalse(UserList.checkUnique(username));
    }
}
