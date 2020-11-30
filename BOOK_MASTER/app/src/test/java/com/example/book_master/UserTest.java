package com.example.book_master;

import com.example.book_master.models.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for User
 * Notice functionality related to Firebase could not be tested (it could not connect to cloud)
 * Therefore, we did not test any function implementing Owner or Borrower interface
 */
public class UserTest {
    private User mockUser() {
        final String email = "lyon233333@gmail.com";
        final String password = "ntd10086";
        final String username = "Shrike";
        final String contactInfo = "QAQ";
        return new User(email, password, username, contactInfo);
    }

    /**
     * Test:    getEmail(),
     *          getPassword(),
     *          getUsername(), setUsername(String username),
     *          getContactInfo(), setContactInfo(String contactInfo)
     * User could not modify its email and password once its initialized
     */
    @Test
    void testSetGet() {
        User temp = mockUser();

        assertTrue(temp.getEmail().equalsIgnoreCase("lyon233333@gmail.com"));
        assertTrue(temp.getPassword().equalsIgnoreCase("ntd10086"));
        assertTrue(temp.getUsername().equalsIgnoreCase("Shrike"));
        assertTrue(temp.getContactInfo().equalsIgnoreCase("QAQ"));

        temp.setUsername("Shrike190");
        temp.setContactInfo("ヽ(✿ﾟ▽ﾟ)ノ");
        assertTrue(temp.getUsername().equalsIgnoreCase("Shrike190"));
        assertTrue(temp.getContactInfo().equalsIgnoreCase("ヽ(✿ﾟ▽ﾟ)ノ"));
    }
}
